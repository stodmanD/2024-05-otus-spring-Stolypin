package com.example.hw17resilience4j.dto.mappers;

import com.example.hw17resilience4j.dto.response.CommentDto;
import com.example.hw17resilience4j.models.Comment;
import org.springframework.stereotype.Component;


@Component
public class CommentMapper {

    public CommentDto toDto(Comment comment) {
        CommentDto result = new CommentDto();
        result.setId(comment.getId());
        result.setText(comment.getText());
        return result;
    }
}
