package com.example.hw11spring_security_authentication.services;


import com.example.hw11spring_security_authentication.dto.response.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(long genreId);
}
