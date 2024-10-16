package com.example.hw15spring_boot_actuator.dto.mappers;

import com.example.hw15spring_boot_actuator.dto.response.GenreDto;
import com.example.hw15spring_boot_actuator.models.Genre;
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
