package com.mykidevs.noteapp;

import com.mykidevs.noteapp.controller.NoteController;
import com.mykidevs.noteapp.model.Note;
import com.mykidevs.noteapp.model.enums.Tags;
import com.mykidevs.noteapp.repository.NoteRepository;
import com.mykidevs.noteapp.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteControllerUnitTests {

    private RestTestClient client;

    @Mock
    private NoteRepository noteRepository;
    @Mock
    private NoteService noteService;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
        NoteController controller = new NoteController(noteService);
        client = RestTestClient.bindToController(controller).build();
    }

    @Test
    void getNoteStats_shouldReturnOkAndJson() {
        String noteId = "123";
        Map<String, Integer> expectedStats = Map.of("note", 2, "yes", 2);

        when(noteService.getStatsByNoteId(noteId)).thenReturn(expectedStats);

        client.get()
                .uri("/v1/api/notes/id/{id}/stats", noteId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.note").isEqualTo(2)
                .jsonPath("$.yes").isEqualTo(2)
                .consumeWith(response -> System.out.println(new String(response.getResponseBody())));
    }

    @Test
    void createNoteWithInvalidTags_shouldReturnBadRequest() {
        String jsonWithBadTag = """
                { "title": "Test",
                  "text": "Text",
                  "tags": ["BUSINESS", "INVALID_TAGS"]
                }
                """;
        client.post()
                .uri("/v1/api/notes/new")
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonWithBadTag)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(System.out::println);
    }

}