package com.example.hw06jpa.dto.mappers;

import com.example.hw06jpa.dto.AuthorDto;
import com.example.hw06jpa.models.Author;
import org.springframework.stereotype.Component;


@Component
public class AuthorMapper {
    public AuthorDto toDto(Author author) {
        AuthorDto result = new AuthorDto();
        result.setId(author.getId());
        result.setFullName(author.getFullName());
        return result;
    }

    public Author toModel(AuthorDto dto) {
        Author result = new Author();
        result.setId(dto.getId());
        result.setFullName(dto.getFullName());
        return result;
    }
}
