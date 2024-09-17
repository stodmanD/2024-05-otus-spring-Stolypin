package com.example.hw08mongo.converters;

import com.example.hw08mongo.models.Genre;
import org.springframework.stereotype.Component;


@Component
public class GenreConverter {
    public String genreToString(Genre genre) {
        return "Id: %s, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
