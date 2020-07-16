package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.utils.MessageUrlComposer;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private final NoteService noteService;
    private final MessageUrlComposer messageUrlComposer;

    public NoteController(NoteService noteService, MessageUrlComposer messageUrlComposer) {
        this.noteService = noteService;
        this.messageUrlComposer = messageUrlComposer;
    }

    @PostMapping("/note")
    public String addOrUpdate(Authentication authentication, NoteForm noteForm) {
        Integer noteId = noteForm.getId();
        if (noteId == null) {
            noteService.createNote(noteForm.getTitle(), noteForm.getDescription(), authentication.getName());
        } else {
            noteService.updateNote(noteId, noteForm.getTitle(), noteForm.getDescription());
        }
        return messageUrlComposer.success("Note has been successfully saved.");
    }

    @PostMapping("/deleteNote/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        noteService.deleteNote(id);
        return messageUrlComposer.success("Note has been successfully deleted.");
    }
}
