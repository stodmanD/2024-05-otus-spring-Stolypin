package com.example.hw08mongo.services;

import com.example.hw08mongo.exceptions.EntityNotFoundException;
import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.models.Comment;
import com.example.hw08mongo.repository.BookRepository;
import com.example.hw08mongo.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Comment> findAllCommentsByBookId(String bookId) {
        List<Comment> comments = commentRepository.getAllCommentsByBookId(bookId);
        if (comments.isEmpty()) {
            throw new EntityNotFoundException("Comments for book with id=%s not found".formatted(bookId));
        }
        return comments;
    }

    @Override
    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Transactional
    @Override
    public Comment insert(String text, String bookId) {
        return save("", text, bookId);
    }

    @Transactional
    @Override
    public Comment update(String id, String text) {
        return save(id, text, "");
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else  {
            new EntityNotFoundException("Comment with id %s not found".formatted(id));
        }
    }

    private Comment save(String id, String text, String bookId) {
        if (id.equals("")) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(bookId)));
            return commentRepository.save(new Comment(text, book));
        }
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %s not found".formatted(id)));
        comment.setText(text);
        return commentRepository.save(comment);
    }
}
