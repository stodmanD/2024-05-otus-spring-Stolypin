package com.example.hw06jpa.converters;

import com.example.hw06jpa.models.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {

    public String authorToString(Author author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());

    }
}
