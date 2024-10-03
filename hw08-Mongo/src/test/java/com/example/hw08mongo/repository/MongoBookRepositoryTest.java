package com.example.hw08mongo.repository;

import com.example.hw08mongo.models.Author;
import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе jpa для работы с книгами ")
@DataMongoTest
class MongoBookRepositoryTest {

    private final static String NEW_BOOK_TITLE = "Title_5";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    @DisplayName("должен загружать книгу по id")
    void shouldReturnCorrectBookById() {
        Book book = mongoTemplate.findAll(Book.class).stream()
                .filter(b -> b.getTitle().equals("Title_2")).findFirst().get();
        String authorName = book.getAuthor().getFullName();
        String genreName = book.getGenre().getName();
        Optional<Book> actualBook = bookRepository.findById(book.getId());
        assertThat(actualBook).isPresent().get()
                .matches(b -> b.getGenre().getName().equals(genreName))
                .matches(b -> b.getAuthor().getFullName().equals(authorName));
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        Author author = mongoTemplate.findAll(Author.class).get(0);
        Genre genre = mongoTemplate.findAll(Genre.class).get(1);
        Book expectedBook = new Book(NEW_BOOK_TITLE, author, genre);
        String id = bookRepository.save(expectedBook).getId();
        Book actualBook = mongoTemplate.findById(id,Book.class);
        assertThat(actualBook)
                .matches(s -> s.getTitle().equals(NEW_BOOK_TITLE))
                .matches(s -> s.getAuthor().getFullName().equals(author.getFullName()))
                .matches(s -> s.getGenre().getName().equals(genre.getName()));
        bookRepository.deleteById(actualBook.getId());
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        Author author = mongoTemplate.findAll(Author.class).get(2);
        Genre genre = mongoTemplate.findAll(Genre.class).get(2);
        String curBookId = mongoTemplate.findAll(Book.class).get(0).getId();
        Book expectedBook = new Book(curBookId, NEW_BOOK_TITLE, author, genre);
        bookRepository.save(expectedBook);
        Book actualBook = mongoTemplate.findById(curBookId, Book.class);
        assertThat(actualBook)
                .matches(s -> s.getTitle().equals(NEW_BOOK_TITLE))
                .matches(s -> s.getAuthor().getFullName().equals(author.getFullName()))
                .matches(s -> s.getGenre().getName().equals(genre.getName()));

    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        Book book = mongoTemplate.findAll(Book.class).get(0);
        bookRepository.deleteById(book.getId());
        assertThat(mongoTemplate.findById(book.getId(), Book.class)).isNull();
        
    }

    @Test
    @DisplayName("должен загружать список всех книг")
    void findAll() {
        var actualBooks = bookRepository.findAll();
        assertEquals(6, actualBooks.size());
        actualBooks.forEach(System.out::println);
    }

}