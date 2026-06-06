package com.yjt.basketball.mapper;

import com.yjt.basketball.dto.row.TopPlayerRow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DashboardMapper {

    List<TopPlayerRow> selectTopPlayersForPage(@Param("limit") int limit);

    long countTeams();

    long countPlayers();

    long countGames();

    long countStats();

    long countActivePlayers();

    long countRetiredPlayers();
}
