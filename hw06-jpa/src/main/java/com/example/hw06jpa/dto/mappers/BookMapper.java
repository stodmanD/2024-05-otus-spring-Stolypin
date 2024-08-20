package com.example.hw06jpa.dto.mappers;

import com.example.hw06jpa.dto.BookDto;
import com.example.hw06jpa.models.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final AuthorMapper authorDtoConverter;

    private final GenreMapper genreDtoConverter;

    private final CommentMapper commentDtoConverter;

    public BookDto toDto(Book book) {
        BookDto result = new BookDto();
        result.setId(book.getId());
        result.setTitle(book.getTitle());
        result.setAuthor(authorDtoConverter.toDto(book.getAuthor()));
        result.setGenres(book.getGenres().stream()
                .map(genreDtoConverter::toDto)
                .toList());
        result.setComments(book.getComments().stream()
                .map(commentDtoConverter::toDto)
                .collect(Collectors.toList()));
        return result;
    }

    public BookDto toDtoAll (Book book) {
        BookDto result = new BookDto();
        result.setId(book.getId());
        result.setTitle(book.getTitle());
        result.setAuthor(authorDtoConverter.toDto(book.getAuthor()));
        result.setGenres(book.getGenres().stream()
                .map(genreDtoConverter::toDto)
                .toList());
        return result;
    }

    public Book toModel(BookDto dto) {
        Book result = new Book();
        result.setId(dto.getId());
        result.setTitle(dto.getTitle());
        result.setAuthor(authorDtoConverter.toModel(dto.getAuthor()));
        result.setGenres(dto.getGenres().stream()
                .map(genreDtoConverter::toModel)
                .toList());
        result.setComments(dto.getComments().stream()
                .map(commentDtoConverter::toModel)
                .collect(Collectors.toList()));
        return result;
    }
}
