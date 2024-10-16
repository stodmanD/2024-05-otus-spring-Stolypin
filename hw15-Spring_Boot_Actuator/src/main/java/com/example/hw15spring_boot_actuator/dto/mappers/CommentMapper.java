package com.example.hw15spring_boot_actuator.dto.mappers;

import com.example.hw15spring_boot_actuator.dto.response.CommentDto;
import com.example.hw15spring_boot_actuator.models.Comment;
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
