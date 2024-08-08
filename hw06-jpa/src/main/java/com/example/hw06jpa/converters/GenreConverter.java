package com.example.hw06jpa.converters;

import com.example.hw06jpa.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public String genreToString(GenreDto genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
