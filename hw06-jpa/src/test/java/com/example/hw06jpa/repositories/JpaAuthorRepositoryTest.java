package com.example.hw06jpa.repositories;

import com.example.hw06jpa.models.Author;
import com.example.hw06jpa.models.Book;
import com.example.hw06jpa.repositories.impl.JpaAuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
@Import({JpaAuthorRepository.class})
class JpaAuthorRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaAuthorRepository jpaAuthorRepository;

    @DisplayName("Should return correct author by id")
    @Test
    void shouldReturnCorrectAuthorById() {
        Book book = em.find(Book.class, 1L);
        Author expectedAuthor = book.getAuthor();

        var actualAuthor = jpaAuthorRepository.findById(expectedAuthor.getId());

        assertThat(actualAuthor).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @DisplayName("Should return correct authors list")
    @Test
    void shouldReturnCorrectAuthorsList() {
        List<Author> authors = jpaAuthorRepository.findAll();

        assertEquals(3, authors.size());
        authors.forEach(System.out::println);
    }
}