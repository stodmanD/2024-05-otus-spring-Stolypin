package com.example.hw11spring_security_authentication.dto.mappers;

import com.example.hw11spring_security_authentication.dto.response.GenreDto;
import com.example.hw11spring_security_authentication.models.Genre;
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
