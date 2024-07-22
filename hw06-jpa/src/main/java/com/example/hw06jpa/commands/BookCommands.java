package com.example.hw06jpa.commands;

import com.example.hw06jpa.converters.BookConverter;
import com.example.hw06jpa.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final BookService bookService;

    private final BookConverter bookConverter;

    @ShellMethod(value = "Find all books", key = "ab")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find book by id", key = "bbid")
    public String findBookById(@ShellOption("id") long id) {
        return bookService.findById(id)
                .map(bookConverter::bookToString)
                .orElse("The Book with id %d not found".formatted(id));
    }

    // bins newBook 1 1
    @ShellMethod(value = "Insert book", key = "bins")
    public String insertBook(@ShellOption("title") String title, @ShellOption("authorId") long authorId,
                             @ShellOption("genreId") long genreId) {
        var savedBook = bookService.create(title, authorId, genreId);
        return bookConverter.bookToString(savedBook);
    }

    // bupd 4 editedBook 3 2
    @ShellMethod(value = "Update book", key = "bupd")
    public String updateBook(@ShellOption("id") long id, @ShellOption("title") String title,
                             @ShellOption("authorId") long authorId, @ShellOption("genreId") long genreId) {
        var savedBook = bookService.update(id, title, authorId, genreId);
        return bookConverter.bookToString(savedBook);
    }

    // bdel 4
    @ShellMethod(value = "Delete book by id", key = "bdel")
    public void deleteBook(@ShellOption("id") long id) {
        bookService.deleteById(id);
    }

}
