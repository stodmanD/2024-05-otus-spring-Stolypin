package com.example.hw08mongo.services;

import com.example.hw08mongo.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    Genre findById(String id);
}
