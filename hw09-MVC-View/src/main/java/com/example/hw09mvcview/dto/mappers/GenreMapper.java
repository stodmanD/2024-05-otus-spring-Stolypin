package com.example.hw09mvcview.dto.mappers;

import com.example.hw09mvcview.dto.response.GenreDto;
import com.example.hw09mvcview.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public Genre fromDto(GenreDto dto) {
        return new Genre(dto.getId(), dto.getName());
    }
}
