package com.example.hw13spring_batch.cache;

import com.example.hw13spring_batch.models.jpa.GenreJpa;
import org.springframework.stereotype.Component;


import java.util.concurrent.ConcurrentHashMap;

@Component
public class GenreCache {

    private ConcurrentHashMap<String, GenreJpa> cache = new ConcurrentHashMap<>();

    public GenreJpa getEntityByKey(String key) {
        if (!cache.containsKey(key)) {
            throw new IllegalArgumentException("Not found Genre with id = %d".formatted(key));
        }
        return cache.get(key);
    }

    public void put(String key, GenreJpa entity) {
        cache.put(key, entity);
    }

    public void clear() {
        cache.clear();
    }
}
