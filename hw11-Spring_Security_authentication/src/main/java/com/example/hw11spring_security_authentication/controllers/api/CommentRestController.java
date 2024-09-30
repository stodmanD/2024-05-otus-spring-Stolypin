package com.example.hw11spring_security_authentication.controllers.api;

import com.example.hw11spring_security_authentication.dto.request.CommentCreateDto;
import com.example.hw11spring_security_authentication.dto.response.CommentDto;
import com.example.hw11spring_security_authentication.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/comment/{id}")
    public List<CommentDto> getCommentsForBook(@PathVariable(name = "id") long bookId) {
        return commentService.findByBookId(bookId);
    }

    @PostMapping("/comment/{id}")
    public CommentDto addComment(@PathVariable(name = "id") long bookId, @RequestBody @Valid CommentCreateDto comment) {
        return commentService.create(bookId, comment.getText());
    }
}
