package com.example.hw08mongo.services.impl;

import com.example.hw08mongo.dto.CommentDto;
import com.example.hw08mongo.dto.mappers.CommentMapper;
import com.example.hw08mongo.exceptions.EntityNotFoundException;
import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.models.Comment;
import com.example.hw08mongo.repositories.BookRepository;
import com.example.hw08mongo.repositories.CommentRepository;
import com.example.hw08mongo.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Transactional
    @Override
    public CommentDto create(String bookId, String commentText) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id = %s not found".formatted(bookId)));
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setBook(book);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CommentDto> findById(String id) {
        return commentRepository.findById(id).map(commentMapper::toDto);
    }

    @Transactional
    @Override
    public CommentDto update(String id, String text) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id = %s not found".formatted(id)));
        comment.setText(text);
        return commentMapper.toDto(commentRepository.save(comment));
    }
}
