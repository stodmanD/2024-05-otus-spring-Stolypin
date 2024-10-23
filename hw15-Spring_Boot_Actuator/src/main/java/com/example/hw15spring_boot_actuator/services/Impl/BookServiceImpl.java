package com.example.hw15spring_boot_actuator.services.Impl;

import com.example.hw15spring_boot_actuator.dto.mappers.BookMapper;
import com.example.hw15spring_boot_actuator.dto.mappers.CommentMapper;
import com.example.hw15spring_boot_actuator.dto.request.BookCreateDto;
import com.example.hw15spring_boot_actuator.dto.request.BookUpdateDto;
import com.example.hw15spring_boot_actuator.dto.response.BookDto;
import com.example.hw15spring_boot_actuator.exceptions.NotFoundException;
import com.example.hw15spring_boot_actuator.models.Author;
import com.example.hw15spring_boot_actuator.models.Book;
import com.example.hw15spring_boot_actuator.models.Genre;
import com.example.hw15spring_boot_actuator.repositories.AuthorRepository;
import com.example.hw15spring_boot_actuator.repositories.BookRepository;
import com.example.hw15spring_boot_actuator.repositories.CommentRepository;
import com.example.hw15spring_boot_actuator.repositories.GenreRepository;
import com.example.hw15spring_boot_actuator.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
