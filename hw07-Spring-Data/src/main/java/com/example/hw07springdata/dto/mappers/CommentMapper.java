package com.example.hw07springdata.dto.mappers;

import com.example.hw07springdata.dto.CommentDto;
import com.example.hw07springdata.models.Comment;
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
