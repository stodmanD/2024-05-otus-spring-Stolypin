package com.example.hw07springdata.converters;

import com.example.hw07springdata.dto.CommentDto;
import org.springframework.stereotype.Component;


@Component
public class CommentConverter {
    public String commentToString(CommentDto comment) {
        return "Id: %d, Text: %s".formatted(comment.getId(), comment.getText());
    }
}
