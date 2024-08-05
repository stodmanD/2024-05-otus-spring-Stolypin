package com.example.hw05jdbs.services;


import com.example.hw05jdbs.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();
}
