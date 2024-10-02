package com.example.hw13spring_batch.service;

import com.example.hw13spring_batch.models.jpa.AuthorJpa;
import com.example.hw13spring_batch.models.mongo.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthorMongoToSqlTransformer {
    private final Map<String, Long> authorsDic;

    private long authorId = 1;

    public AuthorJpa transform(Author author) {
        authorsDic.put(author.getId(), authorId);
        return new AuthorJpa(authorId++, author.getFullName());
    }

    public void cleanUp() {
        authorId = 1;
        authorsDic.clear();
    }
}
