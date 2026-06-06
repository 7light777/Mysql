package com.yjt.basketball.dto;

import java.time.LocalDate;

public record RecentScoutNoteDTO(
        Integer noteId,
        Integer playerId,
        String playerName,
        String playerDisplayName,
        String teamName,
        String teamDisplayName,
        String content,
        Integer potentialRating,
        LocalDate noteDate
) {
}
