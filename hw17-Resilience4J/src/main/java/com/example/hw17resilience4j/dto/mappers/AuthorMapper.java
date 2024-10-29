package com.example.hw17resilience4j.dto.mappers;

import com.example.hw17resilience4j.dto.response.AuthorDto;
import com.example.hw17resilience4j.models.Author;
import org.springframework.stereotype.Component;


@Component
public class AuthorMapper {
    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }

    public Author fromDto(AuthorDto dto) {
        return new Author(dto.getId(), dto.getFullName());
    }
}
