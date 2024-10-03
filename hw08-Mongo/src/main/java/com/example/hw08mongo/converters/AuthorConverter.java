package com.example.hw08mongo.converters;

import com.example.hw08mongo.models.Author;
import org.springframework.stereotype.Component;


@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}
