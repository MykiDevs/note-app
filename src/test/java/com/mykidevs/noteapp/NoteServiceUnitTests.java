package com.mykidevs.noteapp;

import com.mykidevs.noteapp.model.Note;
import com.mykidevs.noteapp.model.enums.Tags;
import com.mykidevs.noteapp.repository.NoteRepository;
import com.mykidevs.noteapp.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class NoteServiceUnitTests {
    @Mock
    private NoteRepository noteRepository;
    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getStatsByNoteId_shouldCorrectlyCountWordsAndSortByDescending() {
        String noteId = "123";
        Note n = new Note("dddd", "Title", Instant.now(), "TYetexzfasfras text hi text hi text hello see me hello", Set.of(Tags.BUSINESS, Tags.IMPORTANT));
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(n));
        Map<String, Integer> result = noteService.getStatsByNoteId(noteId);

        assertEquals(3, result.get("text"));
        assertEquals(2, result.get("hi"));
        assertEquals(2, result.get("hello"));
        assertEquals(1, result.get("me"));
        assertEquals(1, result.get("see"));
    }
}
