package com.example.hw13spring_batch.service;

import com.example.hw13spring_batch.cache.AuthorCache;
import com.example.hw13spring_batch.cache.GenreCache;
import com.example.hw13spring_batch.models.jpa.AuthorJpa;
import com.example.hw13spring_batch.models.jpa.BookJpa;
import com.example.hw13spring_batch.models.jpa.GenreJpa;
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

        BookJpa bookJpa = new BookJpa(Long.parseLong(book.getId()) + 1, book.getTitle()
                , new AuthorJpa(authorsDic.getEntityByKey(book.getAuthor().getId())
                , book.getAuthor().getFullName()), book.getGenres().stream()
                .map(genre -> new GenreJpa(genresDic.getEntityByKey(genre.getId()), genre.getName()))
                .collect(Collectors.toList()));
        log.info("Book name='{}', mongo id = '{}',long id = {}",
                book.getTitle(), book.getId(), bookJpa.getId());
        return bookJpa;
    }

    public void cleanUp() {
        authorsDic.clear();
        genresDic.clear();
    }
}
