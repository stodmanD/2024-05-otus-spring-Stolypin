package com.example.hw08mongo.repositories;

import com.example.hw08mongo.models.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {}
