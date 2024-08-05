package com.example.hw05jdbs.repositories;


import com.example.hw05jdbs.models.Genre;

import java.util.List;
import java.util.Set;

public interface GenreRepository {
    List<Genre> findAll();

    List<Genre> findAllByIds(Set<Long> ids);
}
