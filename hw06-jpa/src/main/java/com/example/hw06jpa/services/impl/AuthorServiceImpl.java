package com.example.hw06jpa.services.impl;

import com.example.hw06jpa.dto.AuthorDto;
import com.example.hw06jpa.dto.mappers.AuthorMapper;
import com.example.hw06jpa.repositories.AuthorRepository;
import com.example.hw06jpa.services.AuthorService;
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
