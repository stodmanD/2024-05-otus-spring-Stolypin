package com.example.hw17resilience4j.services;


import com.example.hw17resilience4j.dto.response.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long authorId);
}
