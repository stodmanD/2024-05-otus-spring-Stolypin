package com.example.hw08mongo.services;


import com.example.hw08mongo.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAllCommentsByBookId(String bookId);

    Optional<Comment> findById(String id);

    Comment insert(String text, String bookId);

    Comment update(String id, String text);

    void deleteById(String id);
}
