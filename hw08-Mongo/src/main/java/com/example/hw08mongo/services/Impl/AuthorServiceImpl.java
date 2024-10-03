package com.example.hw08mongo.services.Impl;

import com.example.hw08mongo.models.Author;
import com.example.hw08mongo.repository.AuthorRepository;
import com.example.hw08mongo.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
