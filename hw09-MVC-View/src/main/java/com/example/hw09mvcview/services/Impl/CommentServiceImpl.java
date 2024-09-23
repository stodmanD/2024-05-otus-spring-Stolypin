package com.example.hw09mvcview.services.Impl;

import com.example.hw09mvcview.dto.mappers.CommentMapper;
import com.example.hw09mvcview.dto.response.CommentDto;
import com.example.hw09mvcview.exceptions.NotFoundException;
import com.example.hw09mvcview.models.Book;
import com.example.hw09mvcview.models.Comment;
import com.example.hw09mvcview.repositories.BookRepository;
import com.example.hw09mvcview.repositories.CommentRepository;
import com.example.hw09mvcview.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Transactional
    @Override
    public CommentDto create(long bookId, String commentText) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id = %d not found".formatted(bookId)));
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setBook(book);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto findById(long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Comment with id = %d not found".formatted(id)));
    }
}
