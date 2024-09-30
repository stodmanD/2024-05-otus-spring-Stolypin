package com.example.hw10springmvc.controllers.api;

import com.example.hw10springmvc.dto.request.BookCreateDto;
import com.example.hw10springmvc.dto.request.BookUpdateDto;
import com.example.hw10springmvc.dto.response.BookDto;
import com.example.hw10springmvc.services.BookService;
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
@RequestMapping("/api")
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/book")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping("/book")
    public BookDto addBook(@RequestBody @Valid BookCreateDto book) {
        return bookService.create(book);
    }

    @PutMapping("/book/{id}")
    public BookDto editBook(@RequestBody @Valid BookUpdateDto book) {
        return bookService.update(book);
    }

    @GetMapping("/book/{id}")
    public BookDto findBook(@PathVariable(name = "id") long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable(name = "id") long id) {
        bookService.deleteById(id);
    }
}
