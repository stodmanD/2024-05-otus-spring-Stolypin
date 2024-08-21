package com.example.hw08mongo.dto.mappers;

import com.example.hw08mongo.dto.GenreDto;
import com.example.hw08mongo.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public GenreDto toDto(Genre genre) {
        GenreDto result = new GenreDto();
        result.setId(genre.getId());
        result.setName(genre.getName());
        return result;
    }
}
