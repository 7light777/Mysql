package com.yjt.basketball.dto;

import java.util.List;

public record DashboardPageDTO(
        DashboardDTO dashboard,
        List<GameListItemDTO> recentGames,
        List<TopPlayerPageDTO> topPlayers,
        long activePlayerCount,
        long retiredPlayerCount
) {
    public record TopPlayerPageDTO(
            Integer playerId,
            String playerName,
            String teamName,
            java.math.BigDecimal avgPoints
    ) {
    }
}
