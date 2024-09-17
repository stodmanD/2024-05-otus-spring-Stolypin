package com.example.hw08mongo.services;

import com.example.hw08mongo.exceptions.EntityNotFoundException;
import com.example.hw08mongo.models.Genre;
import com.example.hw08mongo.repository.GenreRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(String id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id=%s not found".formatted(id)));
        return genre;
    }
}
