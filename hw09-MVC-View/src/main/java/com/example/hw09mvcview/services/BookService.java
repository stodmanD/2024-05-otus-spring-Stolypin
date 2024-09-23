package com.example.hw09mvcview.services;


import com.example.hw09mvcview.dto.request.BookCreateDto;
import com.example.hw09mvcview.dto.request.BookUpdateDto;
import com.example.hw09mvcview.dto.response.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto dto);

    BookDto update(BookUpdateDto dto);

    void deleteById(long id);
}
