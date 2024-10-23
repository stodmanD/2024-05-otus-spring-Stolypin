package com.example.hw12spring_security_acl.services;


import com.example.hw12spring_security_acl.dto.request.BookCreateDto;
import com.example.hw12spring_security_acl.dto.request.BookUpdateDto;
import com.example.hw12spring_security_acl.dto.response.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto dto);

    BookDto update(BookUpdateDto dto);

    void deleteById(long id);
}
