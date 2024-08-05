package com.example.hw05jdbs.converters;

import com.example.hw05jdbs.models.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        return "Id: %d, FullName: %s".formatted(author.id(), author.fullName());
    }
}
