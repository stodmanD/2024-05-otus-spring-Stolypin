package com.example.hw17resilience4j.services;


import com.example.hw17resilience4j.dto.response.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto create(long bookId, String commentText);

    CommentDto findById(long id);

    List<CommentDto> findByBookId(long bookId);
}
