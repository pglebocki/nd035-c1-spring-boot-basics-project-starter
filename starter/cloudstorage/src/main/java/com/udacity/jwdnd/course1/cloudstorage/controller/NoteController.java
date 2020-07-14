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
    public String addNote(Authentication authentication, NoteForm noteForm, Model model) {
        noteService.createNote(noteForm.getTitle(), noteForm.getDescription(), authentication.getName());
        return "redirect:/result";
    }

    @PostMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable Integer id, Model model) {
        noteService.deleteNote(id);
        return "redirect:/result";
    }

    @PostMapping("/updateNote")
    public String updateNote(NoteForm noteForm, Model model) {
        noteService.updateNote(noteForm.getId(), noteForm.getTitle(), noteForm.getDescription());
        return "redirect:/result";
    }
}
