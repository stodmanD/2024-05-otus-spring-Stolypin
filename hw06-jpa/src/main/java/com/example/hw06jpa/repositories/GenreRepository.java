package com.example.hw06jpa.repositories;

import com.example.hw06jpa.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAll();

    Optional<Genre> findById(long id);

}
