package com.example.hw10springmvc.services;


import com.example.hw10springmvc.dto.request.BookCreateDto;
import com.example.hw10springmvc.dto.request.BookUpdateDto;
import com.example.hw10springmvc.dto.response.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto dto);

    BookDto update(BookUpdateDto dto);

    void deleteById(long id);
}
