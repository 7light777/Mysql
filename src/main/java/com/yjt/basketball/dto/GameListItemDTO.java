package com.yjt.basketball.dto;

import java.time.LocalDate;

public record GameListItemDTO(
        Integer gameId,
        LocalDate gameDate,
        Integer homeTeamId,
        String homeTeamName,
        String homeTeamDisplayName,
        String homeTeamAbbr,
        Integer awayTeamId,
        String awayTeamName,
        String awayTeamDisplayName,
        String awayTeamAbbr,
        Integer homeScore,
        Integer awayScore,
        Long statsCount
) {
}
