package com.example.hw06jpa.services;


import com.example.hw06jpa.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Optional<BookDto> findById(long id);

    List<BookDto> findAll();

    BookDto create(String title, long authorId, Set<Long> genreIds);

    BookDto update(long id, String title, long authorId, Set<Long> genreIds);

    void deleteById(long id);
}
