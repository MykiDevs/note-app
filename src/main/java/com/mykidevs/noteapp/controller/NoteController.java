package com.mykidevs.noteapp.controller;

import com.mykidevs.noteapp.dto.NoteCreateRequest;
import com.mykidevs.noteapp.dto.NoteSummaryResponse;
import com.mykidevs.noteapp.dto.NoteUpdateRequest;
import com.mykidevs.noteapp.dto.NoteResponse;
import com.mykidevs.noteapp.model.Note;
import com.mykidevs.noteapp.model.enums.Tags;


import com.mykidevs.noteapp.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


@RestController
@Slf4j
@RequestMapping("/v1/api/notes")
@AllArgsConstructor
@Tag(name = "Note controller", description = "Simple CRUD controller for notes. One controller to rule them all!")
public class NoteController {


    private final NoteService noteService;

    @GetMapping("/")
    @Operation(summary = "Get notes via pagination")
    public Page<NoteSummaryResponse> getNotes(@RequestParam(required = false) Set<Tags> tags,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        return noteService.getNotes(tags, page, size);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Get note data")
    public NoteResponse getNote(@PathVariable String id) {
        return noteService.getNoteById(id);
    }

    @GetMapping("/id/{id}/text")
    @Operation(summary = "Get note text")
    public String getNoteText(@PathVariable final String id) {
        return noteService.getNoteTextById(id);
    }

    @GetMapping("/id/{id}/stats")
    @Operation(summary = "Get note stats")
    public Map<String, Integer> getNoteStats(@PathVariable String id) {
        return noteService.getStatsByNoteId(id);
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Delete note by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable String id) {
        noteService.deleteNoteById(id);
    }

    @PatchMapping("/id/{id}")
    @Operation(summary = "Update note by id")
    public NoteResponse updateNote(@PathVariable String id, @RequestBody NoteUpdateRequest request) {
        return noteService.updateNote(request, id);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new note")
    public NoteResponse createNote(@Valid @RequestBody NoteCreateRequest request) {
        return noteService.createNote(request);
    }








}
