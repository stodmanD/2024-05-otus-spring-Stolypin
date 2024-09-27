package com.example.hw09mvcview.dto.mappers;

import com.example.hw09mvcview.dto.response.CommentDto;
import com.example.hw09mvcview.models.Comment;
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
