package com.mykidevs.noteapp.repository;


import com.mykidevs.noteapp.model.Note;
import com.mykidevs.noteapp.model.enums.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    Note getNoteById(String id);
    Page<Note> findAllByTags(Set<Tags> tags, Pageable pageable);
}
