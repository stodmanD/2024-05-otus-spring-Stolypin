package com.example.hw16docker.dto.mappers;

import com.example.hw16docker.dto.response.CommentDto;
import com.example.hw16docker.models.Comment;
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
