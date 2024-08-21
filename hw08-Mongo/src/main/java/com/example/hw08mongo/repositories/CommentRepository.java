package com.example.hw08mongo.repositories;

import com.example.hw08mongo.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);

    void deleteByBookId(String bookId);
}
