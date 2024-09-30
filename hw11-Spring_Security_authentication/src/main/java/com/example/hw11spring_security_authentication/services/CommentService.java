package com.example.hw11spring_security_authentication.services;


import com.example.hw11spring_security_authentication.dto.response.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto create(long bookId, String commentText);

    CommentDto findById(long id);

    List<CommentDto> findByBookId(long bookId);
}
