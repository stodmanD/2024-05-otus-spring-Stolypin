package com.example.hw11spring_security_authentication.dto.mappers;

import com.example.hw11spring_security_authentication.dto.response.CommentDto;
import com.example.hw11spring_security_authentication.models.Comment;
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
