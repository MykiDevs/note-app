package com.mykidevs.noteapp.dto;


import com.mykidevs.noteapp.model.enums.Tags;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record NoteUpdateRequest(
        @NotEmpty
        String title,
        @NotEmpty
        String text,
        Set<Tags> tags
) {
}
