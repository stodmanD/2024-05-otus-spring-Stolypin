package com.example.hw12spring_security_acl.services;


import com.example.hw12spring_security_acl.dto.response.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto create(long bookId, String commentText);

    CommentDto findById(long id);

    List<CommentDto> findByBookId(long bookId);
}
