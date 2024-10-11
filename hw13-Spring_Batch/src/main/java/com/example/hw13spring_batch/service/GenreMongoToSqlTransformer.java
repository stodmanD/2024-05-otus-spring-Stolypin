package com.example.hw13spring_batch.service;

import com.example.hw13spring_batch.cache.GenreCache;
import com.example.hw13spring_batch.models.jpa.GenreJpa;
import com.example.hw13spring_batch.models.mongo.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class GenreMongoToSqlTransformer {

    private final GenreCache genresDic;

    public GenreJpa transform(Genre genre) {

        GenreJpa genreJpa = new GenreJpa(Long.parseLong(genre.getId()) + 1, genre.getName());
        genresDic.put(genre.getId(), genreJpa.getId());
        log.info("Genre name='{}', mongo id = '{}',long id = {}",
                genre.getName(), genre.getId(), genreJpa.getId());
        return genreJpa;
    }

    public void cleanUp() {
        genresDic.clear();
    }
}
