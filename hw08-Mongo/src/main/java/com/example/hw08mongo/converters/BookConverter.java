package com.example.hw08mongo.converters;

import com.example.hw08mongo.models.Book;
import org.springframework.stereotype.Component;


@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    public BookConverter(AuthorConverter authorConverter, GenreConverter genreConverter) {
        this.authorConverter = authorConverter;
        this.genreConverter = genreConverter;
    }

    public String bookToString(Book book) {
        return "Id: %s, title: %s, author: {%s}, genres: [%s] ".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genreConverter.genreToString(book.getGenre())
                );
    }
}
