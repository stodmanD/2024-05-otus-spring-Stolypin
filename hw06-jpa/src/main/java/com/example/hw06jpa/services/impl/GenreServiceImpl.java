package com.example.hw06jpa.services.impl;

import com.example.hw06jpa.dto.GenreDto;
import com.example.hw06jpa.dto.mappers.GenreMapper;
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

    private final GenreMapper genreMapper;

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .toList();
    }
}
