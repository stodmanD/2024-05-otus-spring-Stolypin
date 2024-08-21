package com.example.hw08mongo.dto.mappers;

import com.example.hw08mongo.dto.CommentDto;
import com.example.hw08mongo.models.Comment;
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
