package com.example.hw08mongo.services;

import com.example.hw08mongo.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Optional<BookDto> findById(String id);

    List<BookDto> findAll();

    BookDto create(String title, String authorId, Set<String> genreIds);

    BookDto update(String id, String title, String authorId, Set<String> genreIds);

    void deleteById(String id);
}
