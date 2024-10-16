package com.example.hw13spring_batch.service;

import com.example.hw13spring_batch.cache.AuthorCache;
import com.example.hw13spring_batch.cache.GenreCache;
import com.example.hw13spring_batch.models.jpa.BookJpa;
import com.example.hw13spring_batch.models.mongo.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookMongoToSqlTransformer {

    private final AuthorCache authorsDic;

    private final GenreCache genresDic;

    public BookJpa transform(Book book) {

        BookJpa bookJpa = new BookJpa(book.getTitle()
                , authorsDic.getEntityByKey(book.getAuthor().getId()), book.getGenres().stream()
                .map(genre -> genresDic.getEntityByKey(genre.getId()))
                .collect(Collectors.toList()));
        log.info("Book name='{}', mongo id = '{}',author id = {}",
                book.getTitle(), book.getId(), book.getAuthor().getId());
        return bookJpa;
    }

    public void cleanUp() {
        authorsDic.clear();
        genresDic.clear();
    }
}
