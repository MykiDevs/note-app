package com.mykidevs.noteapp.model;

import com.mykidevs.noteapp.model.enums.Tags;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Document(collection = "notes")
@ToString
@Setter
@Getter
@AllArgsConstructor
public class Note {
    @Id
    String id;
    @NotEmpty
    @Indexed
    String title;

    @CreatedDate
    @Indexed
    Instant createdAt;
    @NotEmpty
    @Indexed
    String text;

    @Indexed
    Set<Tags> tags = Set.of();
}
