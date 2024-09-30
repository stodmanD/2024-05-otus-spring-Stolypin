package com.example.hw10springmvc.services;


import com.example.hw10springmvc.dto.response.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long authorId);
}
