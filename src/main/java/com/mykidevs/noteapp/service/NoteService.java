package com.mykidevs.noteapp.service;


import com.mykidevs.noteapp.dto.NoteCreateRequest;
import com.mykidevs.noteapp.dto.NoteSummaryResponse;
import com.mykidevs.noteapp.dto.NoteUpdateRequest;
import com.mykidevs.noteapp.exception.NoteNotFoundException;
import com.mykidevs.noteapp.mapper.NoteMapper;
import com.mykidevs.noteapp.model.Note;
import com.mykidevs.noteapp.dto.NoteResponse;
import com.mykidevs.noteapp.model.enums.Tags;
import com.mykidevs.noteapp.repository.NoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class NoteService {
    public final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    private Note getById(String id) {
        return noteRepository.findById(id)
            .orElseThrow(() -> new NoteNotFoundException("Note not found!"));
    }

    public final Page<NoteSummaryResponse> getNotes(final Set<Tags> tags,
                                              final int page,
                                              final int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Note> notePage = (tags == null || tags.isEmpty())
                ? noteRepository.findAll(pageable)
                : noteRepository.findAllByTags(tags, pageable);
        return notePage.map(noteMapper::toSummaryDto);

    }
    public final NoteResponse getNoteById(String id) {
        var note = getById(id);
        return noteMapper.toDto(note);
    }

    public final String getNoteTextById(String id) {
        var note = getById(id);
        return note.getText();
    }
    public final Map<String, Integer> getStatsByNoteId(String id) {
        var note = getById(id);
        String text = note.getText();
        String[] words = text.toLowerCase().split("[\\s\\p{Punct}]+");
        Map<String, Integer> countMap = new HashMap<>();
        for(String word : words) {
            if(!word.isEmpty()) {
                countMap.put(word, countMap.getOrDefault(word,0) + 1);
            }
        }

        return countMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


    public final NoteResponse updateNote(NoteUpdateRequest dto, String id) {
        var existingNote = getById(id);
        noteMapper.updateNoteFromDto(dto, existingNote);
        var updatedNote = noteRepository.save(existingNote);
        return noteMapper.toDto(updatedNote);
    }
    public final NoteResponse createNote(NoteCreateRequest dto) {
        var note = noteMapper.toEntity(dto);
        var savedNote = noteRepository.save(note);
        return noteMapper.toDto(savedNote);
    }

    public final void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }
}
