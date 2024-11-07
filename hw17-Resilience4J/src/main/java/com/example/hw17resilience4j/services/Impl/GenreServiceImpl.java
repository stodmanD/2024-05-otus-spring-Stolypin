package com.example.hw17resilience4j.services.Impl;

import com.example.hw17resilience4j.dto.mappers.GenreMapper;
import com.example.hw17resilience4j.dto.response.GenreDto;
import com.example.hw17resilience4j.exceptions.NotFoundException;
import com.example.hw17resilience4j.repositories.GenreRepository;
import com.example.hw17resilience4j.services.GenreService;
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

    @Override
    public GenreDto findById(long genreId) {
        return genreRepository.findById(genreId)
                .map(genreMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Genre with id = %s not found".formatted(genreId)));
    }
}
