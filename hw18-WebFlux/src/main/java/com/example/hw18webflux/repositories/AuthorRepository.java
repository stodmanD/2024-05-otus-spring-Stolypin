package com.example.hw18webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.example.hw18webflux.models.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {}
