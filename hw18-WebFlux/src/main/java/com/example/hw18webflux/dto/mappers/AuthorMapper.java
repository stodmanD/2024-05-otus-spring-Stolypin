package com.example.hw18webflux.dto.mappers;

import org.springframework.stereotype.Component;
import com.example.hw18webflux.dto.response.AuthorDto;
import com.example.hw18webflux.models.Author;

@Component
public class AuthorMapper {
    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }

    public Author fromDto(AuthorDto dto) {
        return new Author(dto.getId(), dto.getFullName());
    }
}
