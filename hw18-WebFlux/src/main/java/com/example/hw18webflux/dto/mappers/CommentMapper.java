package com.example.hw18webflux.dto.mappers;

import org.springframework.stereotype.Component;
import com.example.hw18webflux.dto.response.CommentDto;
import com.example.hw18webflux.models.Comment;

@Component
public class CommentMapper {

    public CommentDto toDto(Comment comment) {
        CommentDto result = new CommentDto();
        result.setId(comment.getId());
        result.setText(comment.getText());
        return result;
    }
}
