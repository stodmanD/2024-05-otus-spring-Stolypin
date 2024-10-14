package com.example.hw12spring_security_acl.services;


import com.example.hw12spring_security_acl.dto.response.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long authorId);
}
