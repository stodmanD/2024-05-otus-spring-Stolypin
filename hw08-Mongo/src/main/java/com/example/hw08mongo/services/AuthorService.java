package com.example.hw08mongo.services;


import com.example.hw08mongo.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
