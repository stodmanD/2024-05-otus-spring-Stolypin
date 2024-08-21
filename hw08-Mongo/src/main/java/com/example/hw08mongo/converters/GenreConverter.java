package com.example.hw08mongo.converters;

import com.example.hw08mongo.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public String genreToString(GenreDto genre) {
        return "Id: %s, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
