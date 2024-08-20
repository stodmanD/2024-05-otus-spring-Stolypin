package com.example.hw06jpa.services;


import com.example.hw06jpa.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
