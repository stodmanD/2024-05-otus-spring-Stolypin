package com.example.hw11spring_security_authentication.services;


import com.example.hw11spring_security_authentication.dto.response.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long authorId);
}
