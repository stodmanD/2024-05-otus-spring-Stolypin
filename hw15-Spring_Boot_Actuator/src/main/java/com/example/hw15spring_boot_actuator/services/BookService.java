package com.example.hw15spring_boot_actuator.services;


import com.example.hw15spring_boot_actuator.dto.request.BookCreateDto;
import com.example.hw15spring_boot_actuator.dto.request.BookUpdateDto;
import com.example.hw15spring_boot_actuator.dto.response.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto dto);

    BookDto update(BookUpdateDto dto);

    void deleteById(long id);
}
