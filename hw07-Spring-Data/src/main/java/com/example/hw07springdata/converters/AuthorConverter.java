package com.example.hw07springdata.converters;

import com.example.hw07springdata.dto.AuthorDto;
import org.springframework.stereotype.Component;


@Component
public class AuthorConverter {
    public String authorToString(AuthorDto author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}
