package com.mykidevs.noteapp.dto;

import com.mykidevs.noteapp.model.enums.Tags;

import java.time.Instant;
import java.util.Set;

public record NoteResponse(
        String id,
        String title,
        Instant createdAt,
        String text,
        Set<Tags> tags
) {

}
