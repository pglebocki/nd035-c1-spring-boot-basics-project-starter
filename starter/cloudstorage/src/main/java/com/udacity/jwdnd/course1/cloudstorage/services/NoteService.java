package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public int createNote(String title, String description, String userName) {
        User user = userService.getUserByName(userName);
        return noteMapper.insert(new Note(null, title, description, user.getUserId()));
    }

    public void deleteNote(Integer id) {
        noteMapper.delete(id);
    }

    public void updateNote(Integer id, String title, String description) {
        noteMapper.update(new Note(id, title, description, null));
    }

    public List<Note> getAllNotes(String userName) {
        User user = userService.getUserByName(userName);
        if (user != null) {
            return noteMapper.getUserNotes(user.getUserId());
        }
        return new ArrayList<>();
    }
}
