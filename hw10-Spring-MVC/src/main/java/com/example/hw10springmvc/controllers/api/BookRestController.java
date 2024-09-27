package com.example.hw10springmvc.controllers.api;

import com.example.hw10springmvc.dto.request.BookCreateDto;
import com.example.hw10springmvc.dto.request.BookUpdateDto;
import com.example.hw10springmvc.dto.request.CommentCreateDto;
import com.example.hw10springmvc.dto.response.BookDto;
import com.example.hw10springmvc.dto.response.CommentDto;
import com.example.hw10springmvc.services.BookService;
import com.example.hw10springmvc.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class BookRestController {
    private final BookService bookService;

    private final CommentService commentService;

    @GetMapping("")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping("")
    public BookDto addBook(@RequestBody @Valid BookCreateDto book) {
        return bookService.create(book);
    }

    @PutMapping("/{id}")
    public BookDto editBook(@RequestBody @Valid BookUpdateDto book) {
        return bookService.update(book);
    }

    @GetMapping("/{id}")
    public BookDto findBook(@PathVariable(name = "id") long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable(name = "id") long id) {
        bookService.deleteById(id);
    }

    @GetMapping("/{id}/comment")
    public List<CommentDto> getCommentsForBook(@PathVariable(name = "id") long bookId) {
        return commentService.findByBookId(bookId);
    }

    @PostMapping("/{id}/comment")
    public CommentDto addComment(@PathVariable(name = "id") long bookId, @RequestBody @Valid CommentCreateDto comment) {
        return commentService.create(bookId, comment.getText());
    }
}
