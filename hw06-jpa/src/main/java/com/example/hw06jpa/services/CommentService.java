package com.example.hw06jpa.services;


import com.example.hw06jpa.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentDto> create(long bookId, String commentText);

    Optional<CommentDto> findById(long id);

    CommentDto update(long id, String text);
}
