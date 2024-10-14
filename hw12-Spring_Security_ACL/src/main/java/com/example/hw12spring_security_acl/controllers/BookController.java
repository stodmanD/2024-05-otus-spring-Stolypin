package com.example.hw12spring_security_acl.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class BookController {

    @GetMapping("/")
    public String listPage() {
        return "booksList";
    }

    @GetMapping("/book/{id}")
    public String editPage(@PathVariable(name = "id") long id, Model model) {
        model.addAttribute("id", id);
        return "bookEdit";
    }

    @GetMapping("/book")
    public String createPage() {
        return "bookEdit";
    }
}
