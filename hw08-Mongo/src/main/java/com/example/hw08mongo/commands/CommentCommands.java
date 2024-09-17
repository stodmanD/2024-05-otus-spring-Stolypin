package com.example.hw08mongo.commands;

import com.example.hw08mongo.converters.CommentsConverter;
import com.example.hw08mongo.models.Comment;
import com.example.hw08mongo.services.CommentService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentsConverter commentsConverter;

    public CommentCommands(CommentService commentService, CommentsConverter commentsConverter) {
        this.commentService = commentService;
        this.commentsConverter = commentsConverter;
    }

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById(String id) {
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isEmpty()) {
            return "Comment with id %s not found".formatted(id);
        }
        return commentsConverter.commentToString(comment.get());
    }

    @ShellMethod(value = "Insert new comment for book", key = "inc")
    public String insertComment(String text, String bookId) {
        Comment savedComment = commentService.insert(text, bookId);
        return commentsConverter.commentToString(savedComment);
    }

    @ShellMethod(value = "Update comment by ID", key = "ucid")
    public String updateComment(String id, String text) {
        Comment savedComment = commentService.update(id, text);
        return commentsConverter.commentToString(savedComment);
    }

    @ShellMethod(value = "Find all comments by book id", key = "fbbid")
    public String findCommentsByBookId(String bookId) {
        List<Comment> comments = commentService.findAllCommentsByBookId(bookId);
        return commentsConverter.commentsToString(comments);
    }

    @ShellMethod(value = "Delete comment by id", key = "dcbid")
    public String deleteCommentById(String id) {
        commentService.deleteById(id);
        return "Comment with %s deleted".formatted(id);
    }
}
