package com.example.hw13spring_batch.cache;

import com.example.hw13spring_batch.models.jpa.AuthorJpa;
import org.springframework.stereotype.Component;


import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthorCache {

    private ConcurrentHashMap<String, AuthorJpa> cache = new ConcurrentHashMap<>();

    public AuthorJpa getEntityByKey(String key) {
        if (!cache.containsKey(key)) {
            throw new IllegalArgumentException("Not found Author with id = %d".formatted(key));
        }
        return cache.get(key);
    }

    public void put(String key, AuthorJpa entity) {
        cache.put(key, entity);
    }

    public void clear () {
        cache.clear();
    }
}
