package com.example.hw18webflux.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import com.example.hw18webflux.models.Author;
import com.example.hw18webflux.models.Book;
import com.example.hw18webflux.models.Genre;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе ReactiveMongoRepository для работы с книгами ")
class BookRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        Book expectedBook = mongoTemplate.findAll(Book.class).get(0);

        Mono<Book> bookMono = bookRepository.findById(expectedBook.getId());

        StepVerifier.create(bookMono)
                .assertNext(book -> assertThat(book)
                        .usingRecursiveComparison()
                        .isEqualTo(expectedBook))
                .expectComplete()
                .verify();
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        Flux<Book> bookFlux = bookRepository.findAllByOrderByTitleAsc();

        List<Book> books = bookFlux.toStream().toList();
        assertEquals(3, books.size());
        books.forEach(System.out::println);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldSaveNewBook() {
        Author author = mongoTemplate.findAll(Author.class).get(0);
        List<Genre> genres = mongoTemplate.findAll(Genre.class).subList(0, 1);
        var expectedBook =
                new Book(null, "newBook", author, genres);

        Mono<Book> bookMono = bookRepository.save(expectedBook);

        List<Book> recorder = new LinkedList<>();
        StepVerifier.create(bookMono)
                .recordWith(() -> recorder)
                .assertNext(book -> assertThat(book).isNotNull()
                        .matches(b -> b.getId() != null)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .ignoringExpectedNullFields()
                        .isEqualTo(expectedBook))
                .expectComplete()
                .verify();

        Book recordedBook = recorder.get(0);
        Mono<Book> savedBookMono = bookRepository.findById(recordedBook.getId());
        StepVerifier.create(savedBookMono)
                .assertNext(book -> assertThat(book).isNotNull()
                        .usingRecursiveComparison()
                        .isEqualTo(recordedBook))
                .expectComplete()
                .verify();
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

        assertThat(mongoTemplate.findById(changedBook.getId(), Book.class))
                .isNotNull()
                .isNotEqualTo(changedBook);

        Mono<Book> bookMono = bookRepository.save(changedBook);

        List<Book> recorder = new LinkedList<>();
        StepVerifier.create(bookMono)
                .recordWith(() -> recorder)
                .assertNext(book -> assertThat(book)
                        .isNotNull()
                        .usingRecursiveComparison()
                        .ignoringExpectedNullFields()
                        .isEqualTo(changedBook))
                .expectComplete()
                .verify();
        assertThat(mongoTemplate.findById(recorder.get(0).getId(), Book.class))
                .usingRecursiveComparison()
                .isEqualTo(changedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteBook() {
        Book book = mongoTemplate.findAll(Book.class).get(0);
        assertNotNull(mongoTemplate.findById(book.getId(), Book.class));

        Mono<Void> voidMono = bookRepository.deleteById(book.getId());

        StepVerifier.create(voidMono)
                                .expectComplete()
                                        .verify();
        assertNull(mongoTemplate.findById(book.getId(), Book.class));
    }
}