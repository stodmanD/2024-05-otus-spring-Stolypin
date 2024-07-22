package com.example.hw06jpa.repositories;

import com.example.hw06jpa.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    List<Author> findAll();

    Optional<Author> findById(long id);

}
