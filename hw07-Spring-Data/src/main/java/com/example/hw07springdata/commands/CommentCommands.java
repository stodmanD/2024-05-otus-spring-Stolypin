package com.example.hw07springdata.commands;

import com.example.hw07springdata.converters.CommentConverter;
import com.example.hw07springdata.dto.CommentDto;
import com.example.hw07springdata.exceptions.EntityNotFoundException;
import com.example.hw07springdata.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {
    private final CommentService commentService;

    private final CommentConverter commentConverter;

    //ac 1 goodBook
    @ShellMethod(value = "Add comment", key = "ac")
    String addComment(long bookId, String commentText) {
        List<CommentDto> comments = commentService.create(bookId, commentText);
        return comments.stream()
                .map(commentConverter::commentToString)
                .map("{%s}"::formatted)
                .collect(Collectors.joining(", "));
    }

    //cbid 1
    @ShellMethod(value = "Find comment by id", key = "cbid")
    String getComment(long id) {
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id = %d not found".formatted(id)));
    }

    //cupd 1 likeIt
    @ShellMethod(value = "Update comment", key = "cupd")
    String updateComment(long id, String commentText) {
        CommentDto comment = commentService.update(id, commentText);
        return commentConverter.commentToString(comment);
    }
}
