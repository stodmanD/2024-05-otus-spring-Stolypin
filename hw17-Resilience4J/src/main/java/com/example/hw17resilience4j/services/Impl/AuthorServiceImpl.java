package com.example.hw17resilience4j.services.Impl;

import com.example.hw17resilience4j.dto.mappers.AuthorMapper;
import com.example.hw17resilience4j.dto.response.AuthorDto;
import com.example.hw17resilience4j.exceptions.NotFoundException;
import com.example.hw17resilience4j.repositories.AuthorRepository;
import com.example.hw17resilience4j.services.AuthorService;
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

    @Override
    public AuthorDto findById(long authorId) {
        return authorRepository.findById(authorId)
                .map(authorMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Author with id = %s not found".formatted(authorId)));
    }
}
