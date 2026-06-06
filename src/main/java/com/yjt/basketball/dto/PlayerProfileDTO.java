package com.yjt.basketball.dto;

import com.yjt.basketball.dto.row.PlayerGamePointRow;

import java.math.BigDecimal;
import java.util.List;

/**
 * 球员详情页数据（含比赛走势和平均统计）
 */
public record PlayerProfileDTO(
        Integer playerId,
        String name,
        String displayName,
        String position,
        String teamName,
        String teamDisplayName,
        String teamAbbr,
        String statusText,
        String photoUrl,
        List<PlayerGamePointRow> gameStats,
        BigDecimal avgPoints,
        BigDecimal avgRebounds,
        BigDecimal avgAssists,
        BigDecimal avgSteals,
        BigDecimal avgBlocks
) {
}
