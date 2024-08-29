package com.example.hw07springdata.dto.mappers;

import com.example.hw07springdata.dto.AuthorDto;
import com.example.hw07springdata.models.Author;
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
