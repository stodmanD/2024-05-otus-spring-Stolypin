package com.example.hw06jpa.converters;

import com.example.hw06jpa.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    private final CommentConverter commentConverter;

    public String bookToString(BookDto book) {
        String commentsString = "";

        var genresString = book.getGenres().stream()
                .map(genreConverter::genreToString)
                .map("{%s}"::formatted)
                .collect(Collectors.joining(", "));

        if (book.getComments() != null) {
            commentsString = book.getComments().stream()
                    .map(commentConverter::commentToString)
                    .map("{%s}"::formatted)
                    .collect(Collectors.joining(", "));
        }

        return "Id: %d, title: %s, author: {%s}, genres: [%s], comments [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genresString,
                commentsString);
    }
}
