package com.example.hw08mongo.repositories;

import com.example.hw08mongo.models.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе SpringDataMongoDb для работы с авторами ")
class AuthorRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("должен загружать автора по id")
    @Test
    void shouldReturnCorrectAuthorById() {
        Author expectedAuthor = mongoTemplate.findAll(Author.class).get(0);

        var actualAuthor = authorRepository.findById(expectedAuthor.getId());

        assertThat(actualAuthor).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        List<Author> authors = authorRepository.findAll();

        assertEquals(3, authors.size());
        authors.forEach(System.out::println);
    }
}