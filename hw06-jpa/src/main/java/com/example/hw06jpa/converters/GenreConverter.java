package com.example.hw06jpa.converters;

import com.example.hw06jpa.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {

    public String genreToString(Genre genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }

}
