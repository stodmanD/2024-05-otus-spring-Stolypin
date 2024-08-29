package com.example.hw07springdata.services;


import com.example.hw07springdata.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
