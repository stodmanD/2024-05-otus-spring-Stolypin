package com.example.hw06jpa.services.impl;

import com.example.hw06jpa.dto.CommentDto;
import com.example.hw06jpa.dto.mappers.CommentMapper;
import com.example.hw06jpa.exceptions.EntityNotFoundException;
import com.example.hw06jpa.models.Book;
import com.example.hw06jpa.models.Comment;
import com.example.hw06jpa.repositories.BookRepository;
import com.example.hw06jpa.repositories.CommentRepository;
import com.example.hw06jpa.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Transactional
    @Override
    public List<CommentDto> create(long bookId, String commentText) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id = %d not found".formatted(bookId)));
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setBook(book);
        commentRepository.save(comment);
        List<Comment> comments = commentRepository.findByBookId(book.getId());
        return comments.stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CommentDto> findById(long id) {
        return commentRepository.findById(id).map(commentMapper::toDto);
    }

    @Transactional
    @Override
    public CommentDto update(long id, String text) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id = %d not found".formatted(id)));
        comment.setText(text);
        return commentMapper.toDto(commentRepository.save(comment));
    }
}
