package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public Note createNote(String title, String description) {
        User user = userService.getUserByName("t"); // TODO temporary
        Integer id = noteMapper.insert(new Note(null, title, description, user.getUserId()));
        return noteMapper.getNote(id);
    }

    public List<Note> getAllNotes() {
        // TODO consider user id
        return noteMapper.getNotes();
    }
}
