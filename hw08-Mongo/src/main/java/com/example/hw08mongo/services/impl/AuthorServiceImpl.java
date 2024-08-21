package com.example.hw08mongo.services.impl;

import com.example.hw08mongo.dto.AuthorDto;
import com.example.hw08mongo.dto.mappers.AuthorMapper;
import com.example.hw08mongo.repositories.AuthorRepository;
import com.example.hw08mongo.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .toList();
    }
}
