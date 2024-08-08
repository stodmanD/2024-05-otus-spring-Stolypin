package com.example.hw06jpa.repositories;

import com.example.hw06jpa.models.Author;
import com.example.hw06jpa.models.Book;
import com.example.hw06jpa.models.Genre;
import com.example.hw06jpa.repositories.impl.JpaBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import({JpaBookRepository.class})
class JpaBookRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaBookRepository JpaBookrepository;

    @DisplayName("Should return correct book by id")
    @Test
    void shouldReturnCorrectBookById() {
        Book expectedBook = em.find(Book.class, 1);
        var actualBook = JpaBookrepository.findById(expectedBook.getId());
        assertThat(actualBook).isPresent()
                .get()
                .isEqualTo(expectedBook);
    }

    @DisplayName("Should return correct all book`s list")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = JpaBookrepository.findAll();

        assertEquals(3, actualBooks.size());
        actualBooks.forEach(System.out::println);
    }

    @DisplayName("Should save new book")
    @Test
    void shouldSaveNewBook() {
        Author author = em.find(Author.class, 2);
        Genre genre4 = em.find(Genre.class, 4);
        Genre genre5 = em.find(Genre.class, 5);
        var expectedBook =
                new Book(0L, "Title4", author, List.of(genre4, genre5), Collections.emptyList());

        var returnedBook = JpaBookrepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringFields("id").ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(JpaBookrepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("Should save updated book")
    @Test
    void shouldSaveUpdatedBook() {
        Author author = em.find(Author.class, 2);
        Genre genre4 = em.find(Genre.class, 4);
        Genre genre5 = em.find(Genre.class, 5);
        var expectedBook =
                new Book(1L, "BookTitle_10500", author, List.of(genre4, genre5), Collections.emptyList());

        assertThat(JpaBookrepository.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var returnedBook = JpaBookrepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(JpaBookrepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("Should delete book")
    @Test
    void shouldDeleteBook() {
        assertThat(JpaBookrepository.findById(1L)).isPresent();
        JpaBookrepository.deleteById(1L);
        assertThat(JpaBookrepository.findById(1L)).isEmpty();
    }
}