package com.example.hw13spring_batch.cache;

import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

@Component
public class GenreCache {
    private Map<String, Long> cache = new HashMap<>();


    public Long getEntityByKey(String key) {
        if (!cache.containsKey(key)) {
            throw new IllegalArgumentException("Not found Genre with id = %d".formatted(key));
        }
        return cache.get(key);
    }

    public void put(String key, Long entity) {
        cache.put(key, entity);
    }

    public void clear() {
        cache.clear();
    }
}
