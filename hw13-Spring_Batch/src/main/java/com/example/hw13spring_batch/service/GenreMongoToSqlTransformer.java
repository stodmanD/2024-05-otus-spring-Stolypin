package com.example.hw13spring_batch.service;

import com.example.hw13spring_batch.models.jpa.GenreJpa;
import com.example.hw13spring_batch.models.mongo.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Map;

@RequiredArgsConstructor
@Service
public class GenreMongoToSqlTransformer {
    private final Map<String, Long> genresDic;

    private long genreId = 1;

    public GenreJpa transform(Genre genre) {
        genresDic.put(genre.getId(), genreId);
        return new GenreJpa(genreId++, genre.getName());
    }

    public void cleanUp() {
        genreId = 1;
        genresDic.clear();
    }
}
