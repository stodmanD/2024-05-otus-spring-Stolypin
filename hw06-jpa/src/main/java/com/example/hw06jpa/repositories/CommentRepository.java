package com.example.hw06jpa.repositories;


import com.example.hw06jpa.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> findByBookId(long bookId);

    Optional<Comment> findById(long id);

    Comment save(Comment comment);
}
