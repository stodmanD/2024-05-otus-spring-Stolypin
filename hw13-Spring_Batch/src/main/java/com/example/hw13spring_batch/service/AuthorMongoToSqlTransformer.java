package com.example.hw13spring_batch.service;

import com.example.hw13spring_batch.cache.AuthorCache;
import com.example.hw13spring_batch.models.jpa.AuthorJpa;
import com.example.hw13spring_batch.models.mongo.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorMongoToSqlTransformer {

    private final AuthorCache authorsDic;

    public AuthorJpa transform(Author author) {
        AuthorJpa authorJpa = new AuthorJpa(Long.parseLong(author.getId()) + 1, author.getFullName());
        authorsDic.put(author.getId(), authorJpa.getId());
        return authorJpa;
    }

    public void cleanUp() {
        authorsDic.clear();
    }
}
