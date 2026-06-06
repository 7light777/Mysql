package com.yjt.basketball.mapper;

import com.yjt.basketball.dto.row.PlayerGamePointRow;
import com.yjt.basketball.dto.row.PlayerListRow;
import com.yjt.basketball.dto.row.TeamOptionRow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayerMapper {

    List<PlayerListRow> selectPlayers(@Param("playerName") String playerName,
                                      @Param("teamId") Integer teamId,
                                      @Param("position") String position,
                                      @Param("status") String status);

    List<TeamOptionRow> selectTeamOptions();

    /** 球员每场比赛的得分走势（按日期升序） */
    List<PlayerGamePointRow> selectPlayerGamePoints(@Param("playerId") Integer playerId);
}
