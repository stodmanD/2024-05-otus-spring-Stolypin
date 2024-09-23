package com.example.hw09mvcview.services;

import com.example.hw09mvcview.dto.response.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(long genreId);
}
