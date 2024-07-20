package com.example.hw06jpa.services;


import com.example.hw06jpa.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> findAll();

    Optional<Author> findById(long id);

}
