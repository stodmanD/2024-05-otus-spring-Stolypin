package com.example.hw08mongo.services;

import com.example.hw08mongo.dto.CommentDto;

import java.util.Optional;

public interface CommentService {
    CommentDto create(String bookId, String commentText);

    Optional<CommentDto> findById(String id);

    CommentDto update(String id, String text);
}
