package com.yjt.basketball.mapper;

import com.yjt.basketball.dto.UpdateStatsRequest;
import com.yjt.basketball.dto.row.PlayerGameDetailRow;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface StatsMapper {

    List<PlayerGameDetailRow> selectPlayerGameDetails(@Param("playerName") String playerName,
                                                      @Param("gameId") Integer gameId,
                                                      @Param("teamName") String teamName,
                                                      @Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);

    void callUpdatePlayerGameStats(UpdateStatsRequest request);
}
