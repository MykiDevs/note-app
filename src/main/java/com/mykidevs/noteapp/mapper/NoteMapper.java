package com.mykidevs.noteapp.mapper;


import com.mykidevs.noteapp.dto.NoteCreateRequest;
import com.mykidevs.noteapp.dto.NoteResponse;
import com.mykidevs.noteapp.dto.NoteSummaryResponse;
import com.mykidevs.noteapp.dto.NoteUpdateRequest;
import com.mykidevs.noteapp.model.Note;
import com.mykidevs.noteapp.service.NoteService;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NoteMapper {



    NoteSummaryResponse toSummaryDto(Note note);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Note toEntity(NoteCreateRequest noteCreateRequest);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Note toEntity(NoteUpdateRequest noteUpdateRequest);

    NoteResponse toDto(Note note);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNoteFromDto(NoteUpdateRequest dto, @MappingTarget Note note);
}