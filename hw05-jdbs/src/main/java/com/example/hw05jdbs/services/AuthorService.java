package com.example.hw05jdbs.services;


import com.example.hw05jdbs.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
}
