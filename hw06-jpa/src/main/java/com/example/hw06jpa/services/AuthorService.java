package com.example.hw06jpa.services;

import com.example.hw06jpa.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
