package com.example.hw15spring_boot_actuator.services;


import com.example.hw15spring_boot_actuator.dto.response.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long authorId);
}
