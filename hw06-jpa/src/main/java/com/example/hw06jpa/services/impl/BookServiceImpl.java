package com.example.hw06jpa.services.impl;

import com.example.hw06jpa.dto.BookDto;
import com.example.hw06jpa.dto.mappers.BookMapper;
import com.example.hw06jpa.exceptions.EntityNotFoundException;
import com.example.hw06jpa.models.Book;
import com.example.hw06jpa.models.Genre;
import com.example.hw06jpa.repositories.AuthorRepository;
import com.example.hw06jpa.repositories.BookRepository;
import com.example.hw06jpa.repositories.GenreRepository;
import com.example.hw06jpa.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id).map(bookMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
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
        bookRepository.deleteById(id);
    }

    private BookDto save(long id, String title, long authorId, Set<Long> genreIds) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genres = genreRepository.findAllByIds(genreIds);
        Set<Long> foundGenreIds = genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
        if (!foundGenreIds.containsAll(genreIds)) {
            throw new EntityNotFoundException("Not all genres found from list %s".formatted(genreIds));
        }
        var book = new Book(id, title, author, genres, Collections.emptyList());
        return bookMapper.toDto(bookRepository.save(book));
    }
}
