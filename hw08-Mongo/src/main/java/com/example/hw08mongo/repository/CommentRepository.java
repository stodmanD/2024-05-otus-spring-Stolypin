package com.example.hw08mongo.repository;

import com.example.hw08mongo.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> getAllCommentsByBookId(String bookId);

    void deleteAllCommentsByBookId(String bookId);

}
