package com.example.hw06jpa.converters;

import com.example.hw06jpa.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
    public String commentToString(CommentDto comment) {
        return "Id: %d, Text: %s".formatted(comment.getId(), comment.getText());
    }
}
