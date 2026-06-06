package com.yjt.basketball.mapper;

import com.yjt.basketball.dto.row.CurrentStatsRow;
import com.yjt.basketball.dto.row.GameListRow;
import com.yjt.basketball.dto.row.GamePlayerStatsRow;
import com.yjt.basketball.dto.row.GameStatsRowData;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface GameMapper {

    List<GameListRow> selectGames(@Param("gameId") Integer gameId,
                                  @Param("teamName") String teamName,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);

    List<GameListRow> selectRecentGames(@Param("limit") Integer limit);

    GameListRow selectGameById(@Param("gameId") Integer gameId);

    List<GameStatsRowData> selectGameStats(@Param("gameId") Integer gameId,
                                           @Param("homeTeamId") Integer homeTeamId,
                                           @Param("awayTeamId") Integer awayTeamId);

    List<GamePlayerStatsRow> selectGamePlayers(@Param("gameId") Integer gameId,
                                               @Param("homeTeamId") Integer homeTeamId,
                                               @Param("awayTeamId") Integer awayTeamId);

    CurrentStatsRow selectCurrentStats(@Param("playerId") Integer playerId,
                                       @Param("gameId") Integer gameId);

    int countGameById(@Param("gameId") Integer gameId);

    int deletePlayerGameStatsByGameId(@Param("gameId") Integer gameId);

    int deleteGameById(@Param("gameId") Integer gameId);
}
