package com.example.hw07springdata.dto.mappers;

import com.example.hw07springdata.dto.BookDto;
import com.example.hw07springdata.models.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final AuthorMapper authorDtoConverter;

    private final GenreMapper genreDtoConverter;

    public BookDto toDto(Book book) {
        BookDto result = new BookDto();
        result.setId(book.getId());
        result.setTitle(book.getTitle());
        result.setAuthor(authorDtoConverter.toDto(book.getAuthor()));
        result.setGenres(book.getGenres().stream()
                .map(genreDtoConverter::toDto)
                .toList());
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
}
