package com.example.hw08mongo.repositories;

import com.example.hw08mongo.models.Author;
import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.models.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе SpringDataJpa для работы с книгами ")
class BookRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        Book expectedBook = mongoTemplate.findAll(Book.class).get(0);
        var actualBook = bookRepository.findById(expectedBook.getId());
        assertThat(actualBook).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = bookRepository.findAllByOrderByTitleAsc();

        assertEquals(3, actualBooks.size());
        actualBooks.forEach(System.out::println);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldSaveNewBook() {
        Author author = mongoTemplate.findAll(Author.class).get(0);
        List<Genre> genres = mongoTemplate.findAll(Genre.class).subList(0, 1);
        var expectedBook =
                new Book(null, "newBook", author, genres);

        var returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() != null)
                .usingRecursiveComparison().ignoringFields("id").ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookRepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldSaveUpdatedBook() {
        Author author = mongoTemplate.findAll(Author.class).get(2);
        List<Genre> genres = mongoTemplate.findAll(Genre.class).subList(4, 5);

        Book changedBook = mongoTemplate.findAll(Book.class).get(0);
        changedBook.setTitle("newBook");
        changedBook.setAuthor(author);
        changedBook.setGenres(genres);

        assertThat(bookRepository.findById(changedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(changedBook);

        var returnedBook = bookRepository.save(changedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() != null)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(changedBook);

        assertThat(bookRepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteBook() {
        Book book = mongoTemplate.findAll(Book.class).get(0);
        assertThat(bookRepository.findById(book.getId())).isPresent();
        bookRepository.deleteById(book.getId());
        assertThat(bookRepository.findById(book.getId())).isEmpty();
    }
}