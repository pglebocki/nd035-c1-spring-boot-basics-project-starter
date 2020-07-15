package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/result")
public class ResultController {

    @GetMapping("/{status}")
    public String getResult(@PathVariable("status") String status, Model model) {
        Status statusObj = new Status();
        statusObj.setSuccess(status.equals("success"));
        model.addAttribute("status", statusObj);
        return "result";
    }
}
