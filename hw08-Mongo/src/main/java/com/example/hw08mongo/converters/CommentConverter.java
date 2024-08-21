package com.example.hw08mongo.converters;

import com.example.hw08mongo.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
    public String commentToString(CommentDto comment) {
        return "Id: %s, Text: %s".formatted(comment.getId(), comment.getText());
    }
}
