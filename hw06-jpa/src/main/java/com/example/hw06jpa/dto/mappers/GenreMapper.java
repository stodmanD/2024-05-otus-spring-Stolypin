package com.example.hw06jpa.dto.mappers;

import com.example.hw06jpa.dto.GenreDto;
import com.example.hw06jpa.models.Genre;
import org.springframework.stereotype.Component;


@Component
public class GenreMapper {
    public GenreDto toDto(Genre genre) {
        GenreDto result = new GenreDto();
        result.setId(genre.getId());
        result.setName(genre.getName());
        return result;
    }

    public Genre toModel(GenreDto dto) {
        Genre result = new Genre();
        result.setId(dto.getId());
        result.setName(dto.getName());
        return result;
    }
}
