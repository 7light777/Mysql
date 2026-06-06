package com.yjt.basketball.dto;

public record GamePlayerStatsDTO(
        Integer playerId,
        String playerName,
        String displayName,
        String playerDisplayName,
        Integer teamId,
        String teamName,
        String teamDisplayName,
        String teamAbbr,
        String position,
        Integer points,
        Integer rebounds,
        Integer assists,
        Integer steals,
        Integer blocks
) {
}
