package com.example.hw08mongo.converters;

import com.example.hw08mongo.dto.AuthorDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {
    public String authorToString(AuthorDto author) {
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}
