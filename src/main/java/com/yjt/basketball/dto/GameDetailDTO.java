package com.yjt.basketball.dto;

import java.time.LocalDate;
import java.util.List;

public record GameDetailDTO(
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
        List<GameStatsRowDTO> statsList,
        List<GameStatsRowDTO> homeStats,
        List<GameStatsRowDTO> awayStats
) {
}
