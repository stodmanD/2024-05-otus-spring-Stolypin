package com.example.hw06jpa.repositories.impl;

import com.example.hw06jpa.models.Comment;
import com.example.hw06jpa.repositories.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Comment> findByBookId(long bookId) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "select c from Comment c where c.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        Comment comment = entityManager.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            return create(comment);
        }
        return update(comment);
    }

    private Comment update(Comment comment) {
        return entityManager.merge(comment);
    }

    private Comment create(Comment comment) {
        entityManager.persist(comment);
        return comment;
    }
}
