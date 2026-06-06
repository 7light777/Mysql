package com.yjt.basketball.service;

import com.yjt.basketball.dto.DeleteGameResultDTO;
import com.yjt.basketball.dto.row.GameListRow;
import com.yjt.basketball.mapper.GameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private static final Logger log = LoggerFactory.getLogger(GameService.class);
    private static final int CLASSIC_GAME_SCORE_THRESHOLD = 230;

    private final GameMapper gameMapper;

    public GameService(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }

    @Transactional
    public DeleteGameResultDTO deleteGameWithStats(Integer gameId) {
        log.info("===== 事务删除比赛及球员数据 =====");
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

        int deletedStatsCount = gameMapper.deletePlayerGameStatsByGameId(gameId);
        log.info("第一步：删除 PlayerGameStats 记录，影响行数={}", deletedStatsCount);

        // 回滚：第一步 DELETE 之后主动抛异常，证明 @Transactional 会撤销已删除的子表数据。
        if (totalScore >= CLASSIC_GAME_SCORE_THRESHOLD) {
            log.warn("触发事务回滚：该比赛总分 {} >= {}，属于重点比赛，不允许删除",
                    totalScore, CLASSIC_GAME_SCORE_THRESHOLD);
            throw new RuntimeException("该比赛双方总分为 " + totalScore
                    + "，属于重点比赛，不允许删除；事务已回滚，PlayerGameStats 与 Game 均未被删除。");
        }

        gameMapper.deleteGameById(gameId);
        log.info("第二步：删除 Game 记录成功");
        log.info("事务删除完成：gameId={}, 删除统计数据 {} 条", gameId, deletedStatsCount);

        return new DeleteGameResultDTO(gameId, deletedStatsCount);
    }

    private int safeScore(Integer score) {
        return score == null ? 0 : score;
    }
}
