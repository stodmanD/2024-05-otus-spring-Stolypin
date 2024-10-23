package com.example.hw12spring_security_acl.dto.mappers;

import com.example.hw12spring_security_acl.dto.response.AuthorDto;
import com.example.hw12spring_security_acl.models.Author;
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
