package com.example.hw08mongo.repository;

import com.example.hw08mongo.models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе jpa для работы с авторами")
@DataMongoTest
public class MongoAuthorRepositoryTest {

    private final static int EXPECTED_COUNT_AUTHORS = 3;

    private String firstAuthorId;

    private final static String FIRST_AUTHOR_FULL_NAME = "Author_1";

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void init() {
        firstAuthorId = authorRepository.findAll().stream()
                .filter(a -> a.getFullName().equals(FIRST_AUTHOR_FULL_NAME))
                .findFirst().get().getId();
    }


    @DisplayName("Должен загружать список всех авторов")
    @Test
    void shouldReturnCorretAuthorsList() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_COUNT_AUTHORS)
                .allMatch(s -> !s.getFullName().equals(""))
                .anyMatch(s -> s.getFullName().equals("Author_2"));
    }

    @DisplayName("Должен загружать автора по id")
    @Test
    void shouldReturnCorrectAuthorById() {
        Optional<Author> optionalAuthor = authorRepository.findById(firstAuthorId);
        assertThat(optionalAuthor).isPresent().get()
                .matches(a -> a.getFullName().equals(FIRST_AUTHOR_FULL_NAME));
    }
}
