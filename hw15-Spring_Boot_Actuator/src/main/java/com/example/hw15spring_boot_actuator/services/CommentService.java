package com.example.hw15spring_boot_actuator.services;


import com.example.hw15spring_boot_actuator.dto.response.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto create(long bookId, String commentText);

    CommentDto findById(long id);

    List<CommentDto> findByBookId(long bookId);
}
