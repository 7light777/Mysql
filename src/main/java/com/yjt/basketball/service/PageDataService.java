package com.yjt.basketball.service;

import com.yjt.basketball.dto.CurrentStatsDTO;
import com.yjt.basketball.dto.DashboardPageDTO;
import com.yjt.basketball.dto.GameDetailDTO;
import com.yjt.basketball.dto.GameListItemDTO;
import com.yjt.basketball.dto.GamePlayerStatsDTO;
import com.yjt.basketball.dto.GameStatsRowDTO;
import com.yjt.basketball.dto.PlayerListItemDTO;
import com.yjt.basketball.dto.PlayerProfileDTO;
import com.yjt.basketball.dto.RecentScoutNoteDTO;
import com.yjt.basketball.dto.row.CurrentStatsRow;
import com.yjt.basketball.dto.row.GameListRow;
import com.yjt.basketball.dto.row.GamePlayerStatsRow;
import com.yjt.basketball.dto.row.GameStatsRowData;
import com.yjt.basketball.dto.row.PlayerGamePointRow;
import com.yjt.basketball.dto.row.PlayerListRow;
import com.yjt.basketball.dto.row.ScoutNoteRow;
import com.yjt.basketball.dto.row.TeamOptionRow;
import com.yjt.basketball.dto.row.TopPlayerRow;
import com.yjt.basketball.mapper.DashboardMapper;
import com.yjt.basketball.mapper.GameMapper;
import com.yjt.basketball.mapper.PlayerMapper;
import com.yjt.basketball.mapper.ScoutNoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PageDataService {

    private static final Logger log = LoggerFactory.getLogger(PageDataService.class);

    private final DashboardService dashboardService;
    private final DisplayNameService displayNameService;
    private final PlayerMapper playerMapper;
    private final GameMapper gameMapper;
    private final ScoutNoteMapper scoutNoteMapper;
    private final DashboardMapper dashboardMapper;

    public PageDataService(DashboardService dashboardService,
                           DisplayNameService displayNameService,
                           PlayerMapper playerMapper,
                           GameMapper gameMapper,
                           ScoutNoteMapper scoutNoteMapper,
                           DashboardMapper dashboardMapper) {
        this.dashboardService = dashboardService;
        this.displayNameService = displayNameService;
        this.playerMapper = playerMapper;
        this.gameMapper = gameMapper;
        this.scoutNoteMapper = scoutNoteMapper;
        this.dashboardMapper = dashboardMapper;
    }

    public DashboardPageDTO getDashboardPage() {
        return new DashboardPageDTO(
                dashboardService.getDashboard(),
                findRecentGames(5),
                findTopPlayersForPage(),
                countActivePlayers(),
                countRetiredPlayers()
        );
    }

    public List<PlayerListItemDTO> findPlayers(String playerName, Integer teamId) {
        return findPlayers(playerName, teamId, null, null);
    }

    public List<PlayerListItemDTO> findPlayers(String playerName, Integer teamId, String position, String status) {
        log.info("===== 查询球员列表 =====");
        log.info("查询参数：playerName={}, teamId={}, position={}, status={}",
                playerName, teamId, position, status);
        List<PlayerListItemDTO> result = playerMapper.selectPlayers(clean(playerName), teamId, clean(position), normalizeStatus(status))
                .stream()
                .map(this::toPlayerListItem)
                .toList();
        log.info("查询结果数量：{} 条", result.size());
        return result;
    }

    public List<GameListItemDTO> findGames() {
        return findGames(null, null, null, null);
    }

    public List<GameListItemDTO> findGames(Integer gameId, String teamName, LocalDate startDate, LocalDate endDate) {
        log.info("===== 查询比赛列表 =====");
        log.info("查询参数：gameId={}, teamName={}, startDate={}, endDate={}",
                gameId, teamName, startDate, endDate);
        List<GameListItemDTO> result = gameMapper.selectGames(gameId, clean(teamName), startDate, endDate)
                .stream()
                .map(this::toGameListItem)
                .toList();
        log.info("查询结果数量：{} 条", result.size());
        return result;
    }

    public List<GameListItemDTO> findRecentGames(Integer limit) {
        return gameMapper.selectRecentGames(limit == null || limit <= 0 ? 5 : limit)
                .stream()
                .map(this::toGameListItem)
                .toList();
    }

    public GameDetailDTO getGameDetail(Integer gameId) {
        GameListItemDTO game = findGameById(gameId);
        List<GameStatsRowDTO> stats = gameMapper
                .selectGameStats(gameId, game.homeTeamId(), game.awayTeamId())
                .stream()
                .map(this::toGameStatsRow)
                .toList();

        List<GameStatsRowDTO> homeStats = stats.stream()
                .filter(row -> game.homeTeamId().equals(row.teamId()))
                .toList();
        List<GameStatsRowDTO> awayStats = stats.stream()
                .filter(row -> game.awayTeamId().equals(row.teamId()))
                .toList();

        return new GameDetailDTO(
                game.gameId(),
                game.gameDate(),
                game.homeTeamId(),
                game.homeTeamName(),
                game.homeTeamDisplayName(),
                game.homeTeamAbbr(),
                game.awayTeamId(),
                game.awayTeamName(),
                game.awayTeamDisplayName(),
                game.awayTeamAbbr(),
                game.homeScore(),
                game.awayScore(),
                stats,
                homeStats,
                awayStats
        );
    }

    public List<GamePlayerStatsDTO> findGamePlayers(Integer gameId) {
        GameListItemDTO game = findGameById(gameId);
        return gameMapper.selectGamePlayers(gameId, game.homeTeamId(), game.awayTeamId())
                .stream()
                .map(this::toGamePlayerStats)
                .toList();
    }

    public List<RecentScoutNoteDTO> findRecentScoutNotes() {
        return findScoutNotes(null, null, null, null, 0, 10);
    }

    public List<RecentScoutNoteDTO> findScoutNotes(String playerName,
                                                   Integer minRating,
                                                   Integer maxRating,
                                                   String keyword,
                                                   Integer page,
                                                   Integer size) {
        int pageValue = page == null || page < 0 ? 0 : page;
        int sizeValue = size == null || size <= 0 || size > 100 ? 20 : size;
        int offset = pageValue * sizeValue;
        return scoutNoteMapper
                .selectScoutNotes(clean(playerName), minRating, maxRating, clean(keyword), sizeValue, offset)
                .stream()
                .map(this::toScoutNote)
                .toList();
    }

    public CurrentStatsDTO getCurrentStats(Integer playerId, Integer gameId) {
        CurrentStatsRow row = gameMapper.selectCurrentStats(playerId, gameId);
        if (row == null) {
            throw new RuntimeException("未找到对应球员比赛技术统计记录");
        }
        return toCurrentStats(row);
    }

    public List<DashboardPageDTO.TopPlayerPageDTO> findTopPlayersForPage() {
        return dashboardMapper.selectTopPlayersForPage(5)
                .stream()
                .map(this::toTopPlayerPage)
                .toList();
    }

    public List<TeamOption> findTeamOptions() {
        return playerMapper.selectTeamOptions()
                .stream()
                .map(this::toTeamOption)
                .toList();
    }

    public List<String> findPositionOptions() {
        return List.of("PG", "SG", "SF", "PF", "C");
    }

    public long countActivePlayers() {
        return dashboardMapper.countActivePlayers();
    }

    public long countRetiredPlayers() {
        return dashboardMapper.countRetiredPlayers();
    }

    private GameListItemDTO findGameById(Integer gameId) {
        GameListRow row = gameMapper.selectGameById(gameId);
        if (row == null) {
            throw new RuntimeException("比赛不存在，gameId=" + gameId);
        }
        return toGameListItem(row);
    }

    private PlayerListItemDTO toPlayerListItem(PlayerListRow row) {
        String playerName = row.getName();
        String teamName = row.getTeamName();
        String status = row.getStatus();
        String displayName = displayNameService.playerDisplayName(playerName);
        return new PlayerListItemDTO(
                row.getPlayerId(),
                playerName,
                displayName,
                playerName,
                displayNameService.position(row.getPosition(), playerName),
                row.getCurrentTeamId(),
                teamName,
                displayNameService.teamDisplayName(teamName),
                displayNameService.teamAbbr(teamName),
                status,
                "Active".equals(status) ? "现役" : ("Retired".equals(status) ? "退役" : "未知"),
                row.getGamesPlayed(),
                row.getAvgPoints(),
                "/images/players/player-" + row.getPlayerId() + ".png"
        );
    }

    private GameListItemDTO toGameListItem(GameListRow row) {
        String homeTeamName = row.getHomeTeamName();
        String awayTeamName = row.getAwayTeamName();
        return new GameListItemDTO(
                row.getGameId(),
                row.getGameDate(),
                row.getHomeTeamId(),
                homeTeamName,
                displayNameService.teamDisplayName(homeTeamName),
                displayNameService.teamAbbr(homeTeamName),
                row.getAwayTeamId(),
                awayTeamName,
                displayNameService.teamDisplayName(awayTeamName),
                displayNameService.teamAbbr(awayTeamName),
                row.getHomeScore(),
                row.getAwayScore(),
                row.getStatsCount()
        );
    }

    private GameStatsRowDTO toGameStatsRow(GameStatsRowData row) {
        String playerName = row.getPlayerName();
        String teamName = row.getTeamName();
        int points = valueOrZero(row.getPoints());
        int rebounds = valueOrZero(row.getRebounds());
        int assists = valueOrZero(row.getAssists());
        int steals = valueOrZero(row.getSteals());
        int blocks = valueOrZero(row.getBlocks());
        String lineupRole = row.getTeamRank() != null && row.getTeamRank() <= 5 ? "首发" : "替补";
        String rotationLevel = rotationLevel(points, rebounds, assists, steals, blocks);
        return new GameStatsRowDTO(
                row.getPlayerId(),
                playerName,
                displayNameService.playerDisplayName(playerName),
                displayNameService.position(row.getPosition(), playerName),
                row.getTeamId(),
                teamName,
                displayNameService.teamDisplayName(teamName),
                displayNameService.teamAbbr(teamName),
                lineupRole,
                rotationLevel,
                lineupRole,
                points,
                rebounds,
                assists,
                steals,
                blocks
        );
    }

    private GamePlayerStatsDTO toGamePlayerStats(GamePlayerStatsRow row) {
        String teamName = row.getTeamName();
        String playerName = row.getPlayerName();
        return new GamePlayerStatsDTO(
                row.getPlayerId(),
                playerName,
                displayNameService.playerDisplayName(playerName),
                displayNameService.playerDisplayName(playerName),
                row.getTeamId(),
                teamName,
                displayNameService.teamDisplayName(teamName),
                displayNameService.teamAbbr(teamName),
                displayNameService.position(row.getPosition(), playerName),
                valueOrZero(row.getPoints()),
                valueOrZero(row.getRebounds()),
                valueOrZero(row.getAssists()),
                valueOrZero(row.getSteals()),
                valueOrZero(row.getBlocks())
        );
    }

    private CurrentStatsDTO toCurrentStats(CurrentStatsRow row) {
        String playerName = row.getPlayerName();
        String teamName = row.getTeamName();
        return new CurrentStatsDTO(
                row.getPlayerId(),
                playerName,
                displayNameService.playerDisplayName(playerName),
                row.getGameId(),
                row.getTeamId(),
                teamName,
                displayNameService.teamDisplayName(teamName),
                displayNameService.position(row.getPosition(), playerName),
                valueOrZero(row.getPoints()),
                valueOrZero(row.getRebounds()),
                valueOrZero(row.getAssists()),
                valueOrZero(row.getSteals()),
                valueOrZero(row.getBlocks())
        );
    }

    private RecentScoutNoteDTO toScoutNote(ScoutNoteRow row) {
        String playerName = row.getPlayerName();
        String teamName = row.getTeamName();
        return new RecentScoutNoteDTO(
                row.getNoteId(),
                row.getPlayerId(),
                playerName,
                displayNameService.playerDisplayName(playerName),
                teamName,
                displayNameService.teamDisplayName(teamName),
                row.getContent(),
                row.getPotentialRating(),
                row.getNoteDate()
        );
    }

    private DashboardPageDTO.TopPlayerPageDTO toTopPlayerPage(TopPlayerRow row) {
        return new DashboardPageDTO.TopPlayerPageDTO(
                row.getPlayerId(),
                displayNameService.playerDisplayName(row.getPlayerName()),
                displayNameService.teamDisplayName(row.getTeamName()),
                row.getAvgPoints()
        );
    }

    private TeamOption toTeamOption(TeamOptionRow row) {
        String teamName = row.getTeamName();
        return new TeamOption(
                row.getTeamId(),
                teamName,
                displayNameService.teamDisplayName(teamName),
                displayNameService.teamAbbr(teamName)
        );
    }

    private String rotationLevel(int points, int rebounds, int assists, int steals, int blocks) {
        int impact = points + rebounds + assists + steals + blocks;
        if (impact >= 30) {
            return "核心轮换";
        }
        if (impact >= 18) {
            return "主要轮换";
        }
        if (impact >= 8) {
            return "轮换";
        }
        return "边缘轮换";
    }

    private int valueOrZero(Integer value) {
        return value == null ? 0 : value;
    }

    private String clean(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private String normalizeStatus(String status) {
        String value = clean(status);
        if (value == null) {
            return null;
        }
        if ("Active".equalsIgnoreCase(value)) {
            return "Active";
        }
        if ("Retired".equalsIgnoreCase(value)) {
            return "Retired";
        }
        return value;
    }

    public record TeamOption(Integer teamId, String teamName, String teamDisplayName, String teamAbbr) {
    }

    public PlayerProfileDTO getPlayerProfile(Integer playerId) {
        // 先查出该球员的基本信息（从球员列表中定位）
        PlayerListRow basic = playerMapper.selectPlayers(null, null, null, null)
                .stream()
                .filter(p -> p.getPlayerId().equals(playerId))
                .findFirst()
                .orElse(null);
        if (basic == null) {
            throw new RuntimeException("球员不存在，playerId=" + playerId);
        }

        String playerName = basic.getName();
        String teamName = basic.getTeamName();
        List<PlayerGamePointRow> gameStats = playerMapper.selectPlayerGamePoints(playerId);

        // 计算场均统计（用于雷达图）
        double avgPoints = 0, avgRebounds = 0, avgAssists = 0, avgSteals = 0, avgBlocks = 0;
        if (!gameStats.isEmpty()) {
            int n = gameStats.size();
            avgPoints = gameStats.stream().mapToInt(PlayerGamePointRow::points).average().orElse(0);
            avgRebounds = gameStats.stream().mapToInt(PlayerGamePointRow::rebounds).average().orElse(0);
            avgAssists = gameStats.stream().mapToInt(PlayerGamePointRow::assists).average().orElse(0);
            avgSteals = gameStats.stream().mapToInt(PlayerGamePointRow::steals).average().orElse(0);
            avgBlocks = gameStats.stream().mapToInt(PlayerGamePointRow::blocks).average().orElse(0);
        }

        return new PlayerProfileDTO(
                basic.getPlayerId(),
                playerName,
                displayNameService.playerDisplayName(playerName),
                displayNameService.position(basic.getPosition(), playerName),
                teamName,
                displayNameService.teamDisplayName(teamName),
                displayNameService.teamAbbr(teamName),
                "Active".equals(basic.getStatus()) ? "现役" : ("Retired".equals(basic.getStatus()) ? "退役" : "未知"),
                "/images/players/player-" + playerId + ".png",
                gameStats,
                BigDecimal.valueOf(Math.round(avgPoints * 10) / 10.0),
                BigDecimal.valueOf(Math.round(avgRebounds * 10) / 10.0),
                BigDecimal.valueOf(Math.round(avgAssists * 10) / 10.0),
                BigDecimal.valueOf(Math.round(avgSteals * 10) / 10.0),
                BigDecimal.valueOf(Math.round(avgBlocks * 10) / 10.0)
        );
    }
}
