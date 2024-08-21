package com.example.hw08mongo.services;


import com.example.hw08mongo.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
