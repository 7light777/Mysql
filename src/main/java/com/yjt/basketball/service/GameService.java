package com.yjt.basketball.service;

import com.yjt.basketball.dto.DeleteGameResultDTO;
import com.yjt.basketball.dto.row.GameListRow;
import com.yjt.basketball.mapper.GameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameMapper gameMapper;

    public GameService(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }

    public DeleteGameResultDTO deleteGameWithStats(Integer gameId) {
        log.info("===== 数据库事务删除比赛及球员数据 =====");
        log.info("删除参数：gameId={}", gameId);
        GameListRow game = gameMapper.selectGameById(gameId);
        if (game == null) {
            log.warn("删除失败：比赛不存在，gameId={}", gameId);
            throw new RuntimeException("比赛不存在，gameId=" + gameId);
        }

        int totalScore = safeScore(game.getHomeScore()) + safeScore(game.getAwayScore());
        log.info("删除条件检查：{} vs {}，比分 {}:{}，总分={}",
                game.getHomeTeamName(),
                game.getAwayTeamName(),
                game.getHomeScore(),
                game.getAwayScore(),
                totalScore);

        int deletedStatsCount = game.getStatsCount() == null ? 0 : game.getStatsCount().intValue();
        log.info("调用 MySQL 事务过程 sp_delete_game_with_stats_transaction，预期删除统计记录数={}", deletedStatsCount);
        gameMapper.callDeleteGameWithStatsTransaction(gameId);
        log.info("事务删除完成：gameId={}, 删除统计数据 {} 条", gameId, deletedStatsCount);

        return new DeleteGameResultDTO(gameId, deletedStatsCount);
    }

    private int safeScore(Integer score) {
        return score == null ? 0 : score;
    }
}
