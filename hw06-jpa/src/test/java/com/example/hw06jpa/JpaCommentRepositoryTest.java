package com.example.hw06jpa;

import com.example.hw06jpa.models.Author;
import com.example.hw06jpa.models.Book;
import com.example.hw06jpa.models.Comment;
import com.example.hw06jpa.models.Genre;
import com.example.hw06jpa.repositories.impl.JpaCommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DisplayName("Тест репозитория по работе с комментариями")
@DataJpaTest
@Import({JpaCommentRepository.class})
class JpaCommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JpaCommentRepository commentRepository;

    @DisplayName("Should get comment by id")
    @ParameterizedTest
    @MethodSource("getDbComments")
    void shouldGetCommentById(Comment expected) {
        System.out.println(expected);
        var actualComment = commentRepository.findById(expected.getId());
        assertThat(actualComment).isPresent().get().isEqualTo(expected);
    }

    @DisplayName("Should get list comment by bookId")
    @Test
    void shouldGetCommentsByBooksId() {
        var expectedComments = getDbComments();
        var actualComments = commentRepository.findAllCommentByBookId(1);
        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
    }

    @Test
    @DisplayName("Should delete comment by id")
    void shouldDeleteComment() {
        assertThat(commentRepository.findById(1L)).isPresent();
        commentRepository.delete(1L);
        assertThat(commentRepository.findById(1L)).isEmpty();
    }

    @Test
    void saveOrUpdate() {
        var expectedComment = new Comment(1L, "SomeText", getDbBooks().get(0));

        assertThat(commentRepository.findById(expectedComment.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedComment);

        var returnedComment = commentRepository.saveOrUpdate(expectedComment);

        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(commentRepository.findById(expectedComment.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedComment);
    }

    private static List<Comment> getDbComments(List<Book> dbBooks) {
        return List.of(new Comment(1, "This book is cool", dbBooks.get(0)),
                new Comment(2, "Wow! This awesome!", dbBooks.get(0)));
    }

    private static List<Comment> getDbComments() {
        var dbBooks = getDbBooks();
        return getDbComments(dbBooks);
    }

    private static List<Author> getDbAuthors() {
        return List.of(new Author(1, "Nikolay Gogol"), new Author(2, "Fedor Dostoevsky"));
    }

    private static List<Genre> getDbGenres() {
        return List.of(new Genre(1, "Novel"));
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return List.of(new Book(1, "Dead souls", dbAuthors.get(0), dbGenres.get(0)),
                new Book(2, "Crime and punishment", dbAuthors.get(1), dbGenres.get(0)));
    }

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }
}