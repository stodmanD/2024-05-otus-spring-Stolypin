package com.example.hw13spring_batch.service;

import com.example.hw13spring_batch.models.jpa.AuthorJpa;
import com.example.hw13spring_batch.models.jpa.BookJpa;
import com.example.hw13spring_batch.models.jpa.GenreJpa;
import com.example.hw13spring_batch.models.mongo.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookMongoToSqlTransformer {
    private int id = 1;

    private final Map<String, Long> authorsDic;

    private final Map<String, Long> genresDic;

    public BookJpa transform(Book book) {
        BookJpa result = new BookJpa();
        result.setId(id++);
        result.setTitle(book.getTitle());
        AuthorJpa author = new AuthorJpa(authorsDic.get(book.getAuthor().getId()), book.getAuthor().getFullName());
        result.setAuthor(author);

        List<GenreJpa> genres = book.getGenres().stream()
                .map(genre -> new GenreJpa(genresDic.get(genre.getId()), genre.getName()))
                .collect(Collectors.toList());

        result.setGenres(genres);
        return result;
    }

    public void cleanUp() {
        id = 1;
        authorsDic.clear();
        genresDic.clear();
    }
}
