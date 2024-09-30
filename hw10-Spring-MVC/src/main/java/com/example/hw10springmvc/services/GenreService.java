package com.example.hw10springmvc.services;


import com.example.hw10springmvc.dto.response.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(long genreId);
}
