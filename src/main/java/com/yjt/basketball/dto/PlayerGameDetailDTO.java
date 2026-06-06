package com.yjt.basketball.dto;

import java.time.LocalDate;

public record PlayerGameDetailDTO(
        Integer playerId,
        String playerName,
        Integer gameId,
        LocalDate gameDate,
        String homeTeam,
        String awayTeam,
        Integer points,
        Integer rebounds,
        Integer assists,
        Integer steals,
        Integer blocks
) {
}
