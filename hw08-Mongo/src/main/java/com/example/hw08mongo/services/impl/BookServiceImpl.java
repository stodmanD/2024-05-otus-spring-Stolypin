package com.example.hw08mongo.services.impl;

import com.example.hw08mongo.dto.BookDto;
import com.example.hw08mongo.dto.mappers.BookMapper;
import com.example.hw08mongo.dto.mappers.CommentMapper;
import com.example.hw08mongo.exceptions.EntityNotFoundException;
import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.repositories.AuthorRepository;
import com.example.hw08mongo.repositories.BookRepository;
import com.example.hw08mongo.repositories.CommentRepository;
import com.example.hw08mongo.repositories.GenreRepository;
import com.example.hw08mongo.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> findById(String id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .map(this::loadComments);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAllByOrderByTitleAsc().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public BookDto create(String title, String authorId, Set<String> genreIds) {
        return save(null, title, authorId, genreIds);
    }

    @Transactional
    @Override
    @SuppressWarnings("java:S2201")
    public BookDto update(String id, String title, String authorId, Set<String> genreIds) {
        bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(id)));
        return save(id, title, authorId, genreIds);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    private BookDto save(String id, String title, String authorId, Set<String> genreIds) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(authorId)));
        var genres = genreRepository.findAllById(genreIds);
        if (genres.size() != genreIds.size()) {
            throw new EntityNotFoundException("Not all genres found from list %s".formatted(genreIds));
        }
        var book = new Book(id, title, author, genres);
        return bookMapper.toDto(bookRepository.save(book));
    }

    private BookDto loadComments(BookDto dto) {
        dto.setComments(commentRepository.findByBookId(dto.getId()).stream()
                .map(commentMapper::toDto)
                .toList());
        return dto;
    }
}
