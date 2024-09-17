package com.example.hw08mongo.repository;

import com.example.hw08mongo.models.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface GenreRepository extends MongoRepository<Genre, String> {
}
