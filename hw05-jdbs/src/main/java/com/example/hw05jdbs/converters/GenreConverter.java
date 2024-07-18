package com.example.hw05jdbs.converters;

import com.example.hw05jdbs.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public String genreToString(Genre genre) {
        return "Id: %d, Name: %s".formatted(genre.id(), genre.name());
    }
}
