package com.example.hw15spring_boot_actuator.dto.mappers;

import com.example.hw15spring_boot_actuator.dto.response.AuthorDto;
import com.example.hw15spring_boot_actuator.models.Author;
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
