package com.example.hw16docker.services;


import com.example.hw16docker.dto.request.BookCreateDto;
import com.example.hw16docker.dto.request.BookUpdateDto;
import com.example.hw16docker.dto.response.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto dto);

    BookDto update(BookUpdateDto dto);

    void deleteById(long id);
}
