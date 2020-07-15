package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String addOrUpdate(Authentication authentication, NoteForm noteForm) {
        Integer noteId = noteForm.getId();
        if (noteId == null) {
            noteService.createNote(noteForm.getTitle(), noteForm.getDescription(), authentication.getName());
        } else {
            noteService.updateNote(noteId, noteForm.getTitle(), noteForm.getDescription());
        }
        return "redirect:/result/success";
    }

    @PostMapping("/deleteNote/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        noteService.deleteNote(id);
        return "redirect:/result/success";
    }
}
