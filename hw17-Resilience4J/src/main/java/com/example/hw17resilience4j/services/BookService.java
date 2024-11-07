package com.example.hw17resilience4j.services;


import com.example.hw17resilience4j.dto.request.BookCreateDto;
import com.example.hw17resilience4j.dto.request.BookUpdateDto;
import com.example.hw17resilience4j.dto.response.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto dto);

    BookDto update(BookUpdateDto dto);

    void deleteById(long id);
}
