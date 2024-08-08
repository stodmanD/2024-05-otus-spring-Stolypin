package com.example.hw06jpa.converters;

import com.example.hw06jpa.dto.AuthorDto;
import org.springframework.stereotype.Component;


@Component
public class AuthorConverter {
    public String authorToString(AuthorDto author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}
