package com.example.hw08mongo.dto.mappers;

import com.example.hw08mongo.dto.AuthorDto;
import com.example.hw08mongo.models.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public AuthorDto toDto(Author author) {
        AuthorDto result = new AuthorDto();
        result.setId(author.getId());
        result.setFullName(author.getFullName());
        return result;
    }
}
