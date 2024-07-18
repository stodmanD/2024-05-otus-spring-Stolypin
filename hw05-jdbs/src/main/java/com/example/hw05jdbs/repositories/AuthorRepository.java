package com.example.hw05jdbs.repositories;

import com.example.hw05jdbs.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
