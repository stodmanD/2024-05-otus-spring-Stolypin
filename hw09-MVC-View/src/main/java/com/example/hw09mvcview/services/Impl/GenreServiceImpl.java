package com.example.hw09mvcview.services.Impl;

import com.example.hw09mvcview.dto.mappers.GenreMapper;
import com.example.hw09mvcview.dto.response.GenreDto;
import com.example.hw09mvcview.exceptions.NotFoundException;
import com.example.hw09mvcview.repositories.GenreRepository;
import com.example.hw09mvcview.services.GenreService;
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
