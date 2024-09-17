package com.example.hw08mongo.converters;

import com.example.hw08mongo.models.Comment;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentsConverter {
    public String commentToString(Comment comment) {
        return "Id: %s, Text: %s BookId: %s".formatted(comment.getId(), comment.getText(),
                comment.getBook().getId());
    }

    public String commentsToString(List<Comment> comments) {
        return comments == null ? " " : comments.stream().
                map(c -> "Id: %s, Text: %s".formatted(c.getId(),c.getText())).
                collect(Collectors.joining(","));
    }
}
