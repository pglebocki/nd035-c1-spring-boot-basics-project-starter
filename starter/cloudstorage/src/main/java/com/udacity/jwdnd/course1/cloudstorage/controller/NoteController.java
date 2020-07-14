package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public String addNote(NoteForm noteForm, Model model) {
        noteService.createNote(noteForm.getTitle(), noteForm.getDescription());
        return "redirect:/result";
    }

//    @GetMapping
//    public String getNote(Model model) {
////        model.addAttribute("noteForm", new NoteForm());
////        model.addAttribute("notes", this.noteService.getAllNotes());
//        return "home";
//    }
}
