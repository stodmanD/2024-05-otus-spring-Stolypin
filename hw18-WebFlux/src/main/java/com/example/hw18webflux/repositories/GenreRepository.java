package com.example.hw18webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.example.hw18webflux.models.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {}
