package com.example.hw18webflux.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class CommentController {

    @GetMapping("/addComment")
    public String addCommentPage(@RequestParam(name = "id") String id, Model model) {
        model.addAttribute("id", id);
        return "addComment";
    }
}
