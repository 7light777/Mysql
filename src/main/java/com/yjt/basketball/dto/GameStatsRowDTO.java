package com.yjt.basketball.dto;

public record GameStatsRowDTO(
        Integer playerId,
        String playerName,
        String playerDisplayName,
        String position,
        Integer teamId,
        String teamName,
        String teamDisplayName,
        String teamAbbr,
        String lineupRole,
        String rotationLevel,
        String starterLabel,
        Integer points,
        Integer rebounds,
        Integer assists,
        Integer steals,
        Integer blocks
) {
}
