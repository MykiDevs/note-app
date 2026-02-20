package com.mykidevs.noteapp.dto;

import java.time.Instant;

public record NoteSummaryResponse(
        String title,
        Instant createdAt
) {
}
