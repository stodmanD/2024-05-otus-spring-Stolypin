package com.example.hw09mvcview.services.Impl;

import com.example.hw09mvcview.dto.mappers.BookMapper;
import com.example.hw09mvcview.dto.mappers.CommentMapper;
import com.example.hw09mvcview.dto.request.BookCreateDto;
import com.example.hw09mvcview.dto.request.BookUpdateDto;
import com.example.hw09mvcview.dto.response.BookDto;
import com.example.hw09mvcview.exceptions.NotFoundException;
import com.example.hw09mvcview.models.Author;
import com.example.hw09mvcview.models.Book;
import com.example.hw09mvcview.models.Genre;
import com.example.hw09mvcview.repositories.AuthorRepository;
import com.example.hw09mvcview.repositories.BookRepository;
import com.example.hw09mvcview.repositories.CommentRepository;
import com.example.hw09mvcview.repositories.GenreRepository;
import com.example.hw09mvcview.services.BookService;
import lombok.RequiredArgsConstructor;
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
        return bookRepository.findAllByOrderByTitleAsc().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public BookDto create(BookCreateDto dto) {
        var author = findAuthor(dto.getAuthor());
        var genres = findGenres(dto.getGenres());
        Book book = bookMapper.fromDto(dto, author, genres);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    @SuppressWarnings("java:S2201")
    public BookDto update(BookUpdateDto dto) {
        bookRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(dto.getId())));
        var author = findAuthor(dto.getAuthor());
        var genres = findGenres(dto.getGenres());
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
