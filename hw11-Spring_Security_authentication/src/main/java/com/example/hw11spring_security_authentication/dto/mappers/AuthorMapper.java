package com.example.hw11spring_security_authentication.dto.mappers;

import com.example.hw11spring_security_authentication.dto.response.AuthorDto;
import com.example.hw11spring_security_authentication.models.Author;
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
