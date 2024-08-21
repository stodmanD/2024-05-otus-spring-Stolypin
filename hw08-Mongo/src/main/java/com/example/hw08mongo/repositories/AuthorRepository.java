package com.example.hw08mongo.repositories;

import com.example.hw08mongo.models.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, String> {}
