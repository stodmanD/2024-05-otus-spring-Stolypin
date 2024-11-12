package com.example.hw18webflux.dto.mappers;

import org.springframework.stereotype.Component;
import com.example.hw18webflux.dto.response.GenreDto;
import com.example.hw18webflux.models.Genre;

@Component
public class GenreMapper {
    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public Genre fromDto(GenreDto dto) {
        return new Genre(dto.getId(), dto.getName());
    }
}
