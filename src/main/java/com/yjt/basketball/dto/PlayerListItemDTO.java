package com.yjt.basketball.dto;

import java.math.BigDecimal;

public record PlayerListItemDTO(
        Integer playerId,
        String name,
        String displayName,
        String originalName,
        String position,
        Integer teamId,
        String teamName,
        String teamDisplayName,
        String teamAbbr,
        String status,
        String statusText,
        Long gamesPlayed,
        BigDecimal avgPoints,
        String photoUrl
) {
}
