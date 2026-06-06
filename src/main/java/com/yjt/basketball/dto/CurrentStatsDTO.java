package com.yjt.basketball.dto;

public record CurrentStatsDTO(
        Integer playerId,
        String playerName,
        String playerDisplayName,
        Integer gameId,
        Integer teamId,
        String teamName,
        String teamDisplayName,
        String position,
        Integer points,
        Integer rebounds,
        Integer assists,
        Integer steals,
        Integer blocks
) {
}
