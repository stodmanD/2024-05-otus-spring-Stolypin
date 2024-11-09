package com.example.hw17resilience4j.services.Impl;

import com.example.hw17resilience4j.dto.mappers.BookMapper;
import com.example.hw17resilience4j.dto.mappers.CommentMapper;
import com.example.hw17resilience4j.dto.request.BookCreateDto;
import com.example.hw17resilience4j.dto.request.BookUpdateDto;
import com.example.hw17resilience4j.dto.response.BookDto;
import com.example.hw17resilience4j.exceptions.NotFoundException;
import com.example.hw17resilience4j.models.Author;
import com.example.hw17resilience4j.models.Book;
import com.example.hw17resilience4j.models.Genre;
import com.example.hw17resilience4j.repositories.AuthorRepository;
import com.example.hw17resilience4j.repositories.BookRepository;
import com.example.hw17resilience4j.repositories.CommentRepository;
import com.example.hw17resilience4j.repositories.GenreRepository;
import com.example.hw17resilience4j.services.BookService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@CircuitBreaker(name = "daoCircuitBreaker")
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
    public BookDto findById(long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .map(this::loadComments)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll(Sort.by(Sort.Order.asc("title"))).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public BookDto create(BookCreateDto dto) {
        var author = findAuthor(dto.getAuthorId());
        var genres = findGenres(dto.getGenreIds());
        Book book = bookMapper.fromDto(dto, author, genres);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    @SuppressWarnings("java:S2201")
    public BookDto update(BookUpdateDto dto) {
        bookRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(dto.getId())));
        var author = findAuthor(dto.getAuthorId());
        var genres = findGenres(dto.getGenreIds());
        Book book = bookMapper.fromDto(dto, author, genres);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
    }

    private BookDto loadComments(BookDto dto) {
        dto.setComments(commentRepository.findByBookId(dto.getId()).stream()
                .map(commentMapper::toDto)
                .toList());
        return dto;
    }

    private Author findAuthor(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(id)));
    }

    private List<Genre> findGenres(Set<Long> genreIds) {
        var genres = genreRepository.findAllById(genreIds);
        if (genres.size() != genreIds.size()) {
            throw new NotFoundException("Not all genres found from list %s".formatted(genreIds));
        }
        return genres;
    }
}