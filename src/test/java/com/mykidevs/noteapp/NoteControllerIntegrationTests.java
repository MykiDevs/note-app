package com.mykidevs.noteapp;


import com.mykidevs.noteapp.dto.NoteSummaryResponse;
import com.mykidevs.noteapp.model.Note;
import com.mykidevs.noteapp.model.enums.Tags;
import com.mykidevs.noteapp.repository.NoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class NoteControllerIntegrationTests {

    private RestTestClient client;
    @Container
    @ServiceConnection
    static MongoDBContainer mongodb = new MongoDBContainer("mongo:8.0");
    @Autowired
    private NoteRepository noteRepository;
    private Page<NoteSummaryResponse> pageResponse;
    @Autowired
    private WebApplicationContext applicationContext;
    private final Note note1 = new Note("ida123", "Title test", Instant.now(), "Hi! Hello! Hi! Hello! Hi! Helo!", Set.of(Tags.BUSINESS, Tags.IMPORTANT, Tags.PERSONAL));
    private final Note note2 = new Note("ida12dasd3", "Title asdtest", Instant.now(), "Hi! Hasdasdello! Hi! Hello! Hi! Helo!", Set.of(Tags.PERSONAL));
    private final Note note3 = new Note("ida12das3", "Title test", Instant.now(), "Hi! Hello! Hi! Helo!", Set.of(Tags.BUSINESS));

    Note savedNote;

    @BeforeEach
    void setUp() {
        savedNote = noteRepository.save(note1);
        noteRepository.save(note2);
        noteRepository.save(note3);
        client = RestTestClient.bindToApplicationContext(applicationContext).build();

    }

    @AfterEach
    void tearDown() {
        noteRepository.deleteAll();

    }
    @Test
    void createNote_shouldCreateNoteWithValidData() {

        client.post().uri("/v1/api/notes/new")
                .body(note1)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Title test")
                .jsonPath("$.id").value(id -> assertNotEquals(id, note1.getId()))
                .consumeWith(System.out::println);
    }

    @Test
    void deleteNoteById_shouldDeleteNoteById() {

        client.delete().uri("/v1/api/notes/id/{id}", savedNote.getId())
                .exchange()
                .expectStatus().isNoContent();
    }
    @Test
    void listAllNotes_shouldReturnPageWithNotesSortedByRecent() {

        client.get().uri("/v1/api/notes/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.page.size").isEqualTo(5)
                .jsonPath("$.page.totalElements").isEqualTo(3)
                .consumeWith(System.out::println);
    }

    @Test
    void listAllNotes_shouldReturnPageWithNotesAndFilteredByTags() {

        client.get().uri("/v1/api/notes/?tags=BUSINESS")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.page.size").isEqualTo(5)
                .jsonPath("$.page.totalElements").isEqualTo(1)
                .consumeWith(System.out::println);
    }



}
