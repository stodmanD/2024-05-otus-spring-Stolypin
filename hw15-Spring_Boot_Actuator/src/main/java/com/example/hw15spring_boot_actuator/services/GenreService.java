package com.example.hw15spring_boot_actuator.services;


import com.example.hw15spring_boot_actuator.dto.response.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(long genreId);
}
