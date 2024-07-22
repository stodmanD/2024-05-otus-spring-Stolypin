package com.example.hw06jpa.repositories;


import com.example.hw06jpa.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(long id);

    List<Comment> findAllCommentByBookId(long bookId);

    void delete(long id);

    Comment saveOrUpdate(Comment comment);

}
