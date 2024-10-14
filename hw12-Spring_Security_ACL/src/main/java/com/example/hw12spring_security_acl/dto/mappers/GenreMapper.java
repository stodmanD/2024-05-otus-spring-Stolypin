package com.example.hw12spring_security_acl.dto.mappers;

import com.example.hw12spring_security_acl.dto.response.GenreDto;
import com.example.hw12spring_security_acl.models.Genre;
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
