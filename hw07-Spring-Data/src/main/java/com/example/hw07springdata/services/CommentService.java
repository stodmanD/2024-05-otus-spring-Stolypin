package com.example.hw07springdata.services;

import com.example.hw07springdata.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentDto> create(long bookId, String commentText);

    Optional<CommentDto> findById(long id);

    CommentDto update(long id, String text);

    void deleteById(long id);
}
