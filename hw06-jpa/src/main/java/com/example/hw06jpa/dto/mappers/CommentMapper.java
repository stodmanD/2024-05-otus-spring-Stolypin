package com.example.hw06jpa.dto.mappers;

import com.example.hw06jpa.dto.CommentDto;
import com.example.hw06jpa.models.Comment;
import org.springframework.stereotype.Component;


@Component
public class CommentMapper {

    public CommentDto toDto(Comment comment) {
        CommentDto result = new CommentDto();
        result.setId(comment.getId());
        result.setText(comment.getText());
        return result;
    }

    public Comment toModel(CommentDto dto) {
        Comment result = new Comment();
        result.setId(dto.getId());
        result.setText(dto.getText());
        return result;
    }
}
