package com.example.hw07springdata.converters;

import com.example.hw07springdata.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public String genreToString(GenreDto genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
