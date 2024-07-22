package com.example.hw06jpa.services.impl;

import com.example.hw06jpa.models.Genre;
import com.example.hw06jpa.repositories.GenreRepository;
import com.example.hw06jpa.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

}
