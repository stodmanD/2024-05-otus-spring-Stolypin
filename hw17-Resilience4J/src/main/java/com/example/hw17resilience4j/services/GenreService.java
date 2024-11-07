package com.example.hw17resilience4j.services;


import com.example.hw17resilience4j.dto.response.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(long genreId);
}
