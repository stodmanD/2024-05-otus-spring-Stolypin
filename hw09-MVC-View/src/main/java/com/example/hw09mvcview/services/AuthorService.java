package com.example.hw09mvcview.services;


import com.example.hw09mvcview.dto.response.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long authorId);
}
