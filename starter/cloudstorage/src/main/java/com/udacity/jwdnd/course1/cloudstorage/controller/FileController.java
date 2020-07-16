package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.utils.MessageUrlComposer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class FileController {

    private final FileService fileService;
    private final MessageUrlComposer messageUrlComposer;

    public FileController(FileService fileService, MessageUrlComposer messageUrlComposer) {
        this.fileService = fileService;
        this.messageUrlComposer = messageUrlComposer;
    }

    @GetMapping("/downloadFile/{fileid}")
    public ResponseEntity downloadFile(@PathVariable Integer fileid) {
        File file = fileService.getFile(fileid);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFiledata());
    }


    @PostMapping("/uploadFile")
    public String addOrUpdate(@RequestParam("fileUpload") MultipartFile file, Authentication authentication) {
        String filename = file.getOriginalFilename();
        if (fileService.fileExist(filename)) {
            return messageUrlComposer.success("This file already exist.");
        } else {
            try {
                fileService.createFile(file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), file.getBytes(), authentication.getName());
            } catch (IOException ex) {
                return messageUrlComposer.success("Cannot upload file.");
            }
        }

        return messageUrlComposer.success("File has been successfully uploaded.");
    }

    @PostMapping("/deleteFile/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        fileService.deleteFile(id);
        return messageUrlComposer.success("File has been successfully deleted.");
    }
}

