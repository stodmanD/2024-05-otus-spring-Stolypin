package com.example.hw12spring_security_acl.repositories;

import com.example.hw12spring_security_acl.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(long bookId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Comment c where c.book.id = :bookId")
    void deleteByBookId(@Param("bookId") long bookId);
}
