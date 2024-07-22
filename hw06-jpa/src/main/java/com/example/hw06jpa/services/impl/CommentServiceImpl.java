package com.example.hw06jpa.services.impl;

import com.example.hw06jpa.exceptions.EntityNotFoundException;
import com.example.hw06jpa.models.Comment;
import com.example.hw06jpa.repositories.impl.JpaBookRepository;
import com.example.hw06jpa.repositories.impl.JpaCommentRepository;
import com.example.hw06jpa.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final JpaCommentRepository commentRepository;

    private final JpaBookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public Comment create(String text, long bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Not found book with id = %d".formatted(bookId)));
        var comment = new Comment(0, text, book);
        return commentRepository.saveOrUpdate(comment);
    }

    @Override
    @Transactional
    public Comment update(long id, String text, long bookId) {
        var comment = commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Comment with id = %d not found".formatted(id)));
        return commentRepository.saveOrUpdate(comment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findCommentsByBookId(long bookId) {
        return commentRepository.findAllCommentByBookId(bookId);
    }

}
