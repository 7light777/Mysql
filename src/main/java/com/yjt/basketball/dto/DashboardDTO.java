package com.yjt.basketball.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardDTO(
        long teamCount,
        long playerCount,
        long gameCount,
        long statsCount,
        List<TopPlayerDTO> topPlayersByAvgPoints
) {
    public record TopPlayerDTO(Integer playerId, String playerName, BigDecimal avgPoints) {
    }
}
