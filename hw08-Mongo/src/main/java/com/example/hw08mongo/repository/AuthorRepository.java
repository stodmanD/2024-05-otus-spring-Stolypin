package com.example.hw08mongo.repository;

import com.example.hw08mongo.models.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
