package com.example.hw08mongo.repository;

import com.example.hw08mongo.models.Author;
import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе jpa для работы с книгами ")
@DataMongoTest
class MongoBookRepositoryTest {

    private final static String NEW_BOOK_TITLE = "Title_5";

    private final static String BOOK_ID_SUFFIX = "b";

    private List<Author> authorList;

    private List<Genre> genreList;

    private List<Book> bookList;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    public void init() {
        authorList = authorRepository.findAll();
        genreList = genreRepository.findAll();
        bookList = bookRepository.findAll()
                .stream().filter(b -> b.getTitle().contains("Title_")).toList();
    }

    @Test
    @DisplayName("должен загружать книгу по id")
    void shouldReturnCorrectBookById() {
        Book book = bookList.stream()
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
        Author author = authorList.get(1);
        Genre genre = genreList.get(1);
        Book expectedBook = new Book(NEW_BOOK_TITLE, author, genre);
        String id = bookRepository.save(expectedBook).getId();
        Book actualBook = bookRepository.findById(id).get();
        assertThat(actualBook)
                .matches(s -> s.getTitle().equals(NEW_BOOK_TITLE))
                .matches(s -> s.getAuthor().getFullName().equals(author.getFullName()))
                .matches(s -> s.getGenre().getName().equals(genre.getName()));
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        Author author = authorList.get(2);
        Genre genre = genreList.get(2);
        String curBookId = bookList.get(0).getId();
        Book expectedBook = new Book(curBookId, NEW_BOOK_TITLE, author, genre);
        bookRepository.save(expectedBook);
        Book actualBook = bookRepository.findById(curBookId).get();
        assertThat(actualBook)
                .matches(s -> s.getTitle().equals(NEW_BOOK_TITLE))
                .matches(s -> s.getAuthor().getFullName().equals(author.getFullName()))
                .matches(s -> s.getGenre().getName().equals(genre.getName()));

    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        String bookId = bookList.get(bookList.size()-1).getId();
        bookRepository.deleteById(bookId);
        Optional<Book> deletedBook = bookRepository.findById(bookId);
        assertThat(deletedBook).isNotPresent();
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> getAuthor(Integer.toString(id)))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> getGenre(Integer.toString(id)))
                .toList();
    }

    private static List<Book> getDbBooksList(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id + BOOK_ID_SUFFIX, "BookTitle_" + id,
                        dbAuthors.get(id - 1),
                        dbGenres.get(id - 1)))
                .toList();
    }
    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooksList(dbAuthors, dbGenres);
    }

    private static Author getAuthor(String id) {
        return new Author("Author_" + id);
    }


    private static Genre getGenre(String id) {
        return new Genre("Genre_" + id);
    }
}