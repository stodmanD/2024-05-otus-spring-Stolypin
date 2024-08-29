package com.example.hw07springdata.services;


import com.example.hw07springdata.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
