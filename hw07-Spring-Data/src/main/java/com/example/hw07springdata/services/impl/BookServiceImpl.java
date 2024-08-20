package com.example.hw07springdata.services.impl;

import com.example.hw07springdata.dto.BookDto;
import com.example.hw07springdata.dto.mappers.BookMapper;
import com.example.hw07springdata.dto.mappers.CommentMapper;
import com.example.hw07springdata.exceptions.EntityNotFoundException;
import com.example.hw07springdata.models.Book;
import com.example.hw07springdata.repositories.AuthorRepository;
import com.example.hw07springdata.repositories.BookRepository;
import com.example.hw07springdata.repositories.CommentRepository;
import com.example.hw07springdata.repositories.GenreRepository;
import com.example.hw07springdata.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .map(this::loadComments);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAllByOrderByTitleAsc().stream()
                .map(bookMapper::toDtoAll)
                .toList();
    }

    @Transactional
    @Override
    public BookDto create(String title, long authorId, Set<Long> genreIds) {
        return save(0, title, authorId, genreIds);
    }

    @Transactional
    @Override
    @SuppressWarnings("java:S2201")
    public BookDto update(long id, String title, long authorId, Set<Long> genreIds) {
        bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        return save(id, title, authorId, genreIds);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
    }

    private BookDto save(long id, String title, long authorId, Set<Long> genreIds) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genres = genreRepository.findAllById(genreIds);
        if (genres.size() != genreIds.size()) {
            throw new EntityNotFoundException("Not all genres found from list %s".formatted(genreIds));
        }
        var book = new Book(id, title, author, genres, Collections.emptyList());
        return bookMapper.toDto(bookRepository.save(book));
    }

    private BookDto loadComments(BookDto dto) {
        dto.setComments(commentRepository.findByBookId(dto.getId()).stream()
                .map(commentMapper::toDto)
                .toList());
        return dto;
    }
}
