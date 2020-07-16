package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.utils.MessageUrlComposer;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {

    private final CredentialService credentialService;
    private final MessageUrlComposer messageUrlComposer;

    public CredentialController(CredentialService credentialService, MessageUrlComposer messageUrlComposer) {
        this.credentialService = credentialService;
        this.messageUrlComposer = messageUrlComposer;
    }

    @PostMapping("/credential")
    public String addOrUpdate(Authentication authentication, CredentialForm form) {
        Integer credentialId = form.getId();
        if (credentialId == null) {
            credentialService.createCredential(form.getUrl(), form.getUsername(), form.getPassword(), authentication.getName());
        } else {
            credentialService.updateCredential(credentialId, form.getUrl(), form.getUsername(), form.getPassword());
        }
        return messageUrlComposer.success("Credentials have been successfully saved.");
    }

    @PostMapping("/deleteCredential/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        credentialService.deleteCredential(id);
        return messageUrlComposer.success("Credentials have been successfully deleted.");
    }
}
