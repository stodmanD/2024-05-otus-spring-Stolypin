package com.example.hw12spring_security_acl.dto.mappers;

import com.example.hw12spring_security_acl.dto.response.CommentDto;
import com.example.hw12spring_security_acl.models.Comment;
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
