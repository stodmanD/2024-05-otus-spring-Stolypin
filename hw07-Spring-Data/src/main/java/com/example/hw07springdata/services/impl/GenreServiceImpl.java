package com.example.hw07springdata.services.impl;

import com.example.hw07springdata.dto.GenreDto;
import com.example.hw07springdata.dto.mappers.GenreMapper;
import com.example.hw07springdata.repositories.GenreRepository;
import com.example.hw07springdata.services.GenreService;
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
