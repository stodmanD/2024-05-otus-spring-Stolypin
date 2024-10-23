package com.example.hw13spring_batch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CleanUpService {
    private final AuthorMongoToSqlTransformer authorTransformer;

    private final GenreMongoToSqlTransformer genreTransformer;

    private final BookMongoToSqlTransformer bookTransformer;

    public void cleanUp() {
        authorTransformer.cleanUp();
        genreTransformer.cleanUp();
        bookTransformer.cleanUp();
    }
}
