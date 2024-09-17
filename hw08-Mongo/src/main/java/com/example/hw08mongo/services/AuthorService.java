package com.example.hw08mongo.services;


import com.example.hw08mongo.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
}
