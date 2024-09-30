package com.example.hw09mvcview.repositories;

import com.example.hw09mvcview.models.Author;
import com.example.hw09mvcview.models.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе SpringDataJpa для работы с авторами ")
@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("должен загружать автора по id")
    @Test
    void shouldReturnCorrectAuthorById() {
        Book book = em.find(Book.class, 1L);
        Author expectedAuthor = book.getAuthor();

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