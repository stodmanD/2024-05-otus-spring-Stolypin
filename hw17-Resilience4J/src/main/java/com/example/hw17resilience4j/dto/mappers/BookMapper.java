package com.example.hw17resilience4j.dto.mappers;

import com.example.hw17resilience4j.dto.request.BookCreateDto;
import com.example.hw17resilience4j.dto.request.BookUpdateDto;
import com.example.hw17resilience4j.dto.response.BookDto;
import com.example.hw17resilience4j.models.Author;
import com.example.hw17resilience4j.models.Book;
import com.example.hw17resilience4j.models.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public BookDto toDto(Book book) {
        BookDto result = new BookDto();
        result.setId(book.getId());
        result.setTitle(book.getTitle());
        result.setAuthor(authorMapper.toDto(book.getAuthor()));
        result.setGenres(book.getGenres().stream()
                .map(genreMapper::toDto)
                .toList());
        return result;
    }

    public Book fromDto(BookUpdateDto dto, Author author, List<Genre> genres) {
        Book result = new Book();
        result.setId(dto.getId());
        result.setTitle(dto.getTitle());
        result.setAuthor(author);
        result.setGenres(genres);
        return result;
    }

    public Book fromDto(BookCreateDto dto, Author author, List<Genre> genres) {
        Book result = new Book();
        result.setTitle(dto.getTitle());
        result.setAuthor(author);
        result.setGenres(genres);
        return result;
    }
}
