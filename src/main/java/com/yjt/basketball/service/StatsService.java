package com.yjt.basketball.service;

import com.yjt.basketball.dto.PlayerGameDetailDTO;
import com.yjt.basketball.dto.UpdateStatsRequest;
import com.yjt.basketball.dto.row.PlayerGameDetailRow;
import com.yjt.basketball.mapper.StatsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatsService {

    private static final Logger log = LoggerFactory.getLogger(StatsService.class);

    private final StatsMapper statsMapper;

    public StatsService(StatsMapper statsMapper) {
        this.statsMapper = statsMapper;
    }

    public List<PlayerGameDetailDTO> findDetails(String playerName,
                                                 Integer gameId,
                                                 String teamName,
                                                 LocalDate startDate,
                                                 LocalDate endDate) {
        log.info("===== 查询球员比赛数据详情 =====");
        log.info("查询参数：playerName={}, gameId={}, teamName={}, startDate={}, endDate={}",
                playerName, gameId, teamName, startDate, endDate);
        List<PlayerGameDetailDTO> result = statsMapper
                .selectPlayerGameDetails(clean(playerName), gameId, clean(teamName), startDate, endDate)
                .stream()
                .map(this::toPlayerGameDetailDTO)
                .toList();
        log.info("查询结果数量：{} 条", result.size());
        return result;
    }

    public void updateStats(UpdateStatsRequest request) {
        log.info("===== 更新球员比赛数据 =====");
        log.info("更新参数：playerId={}, gameId={}, points={}, rebounds={}, assists={}, steals={}, blocks={}",
                request.playerId(), request.gameId(),
                request.points(), request.rebounds(), request.assists(),
                request.steals(), request.blocks());
        statsMapper.callUpdatePlayerGameStats(request);
        log.info("调用存储过程 sp_update_player_game_stats 成功，SP 自动根据得分差值同步更新对应球队比分");
    }

    private PlayerGameDetailDTO toPlayerGameDetailDTO(PlayerGameDetailRow row) {
        return new PlayerGameDetailDTO(
                row.getPlayerId(),
                row.getPlayerName(),
                row.getGameId(),
                row.getGameDate(),
                row.getHomeTeam(),
                row.getAwayTeam(),
                row.getPoints(),
                row.getRebounds(),
                row.getAssists(),
                row.getSteals(),
                row.getBlocks()
        );
    }

    private String clean(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
