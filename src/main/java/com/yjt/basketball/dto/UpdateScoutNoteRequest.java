package com.yjt.basketball.dto;

import java.time.LocalDate;

public record UpdateScoutNoteRequest(
        String content,
        Integer potentialRating,
        LocalDate noteDate
) {
}
