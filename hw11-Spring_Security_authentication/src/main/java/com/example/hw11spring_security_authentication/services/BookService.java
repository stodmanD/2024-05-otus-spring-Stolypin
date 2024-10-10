package com.example.hw11spring_security_authentication.services;


import com.example.hw11spring_security_authentication.dto.request.BookCreateDto;
import com.example.hw11spring_security_authentication.dto.request.BookUpdateDto;
import com.example.hw11spring_security_authentication.dto.response.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto dto);

    BookDto update(BookUpdateDto dto);

    void deleteById(long id);
}
