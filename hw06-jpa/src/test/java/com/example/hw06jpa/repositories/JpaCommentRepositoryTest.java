package com.example.hw06jpa.repositories;

import com.example.hw06jpa.models.Book;
import com.example.hw06jpa.models.Comment;
import com.example.hw06jpa.repositories.impl.JpaCommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import({JpaCommentRepository.class})
class JpaCommentRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaCommentRepository jpaCommentRepository;

    @DisplayName("Should return correct comment by id")
    @Test
    void shouldReturnCorrectCommentById() {
        Book book = em.find(Book.class, 1L);
        Comment expectedComment = book.getComments().get(0);

        var actualComment = jpaCommentRepository.findById(expectedComment.getId());

        assertThat(actualComment).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedComment);
    }

    @DisplayName("Should return correct comment`s list for book")
    @Test
    void shouldReturnCorrectCommentsListForBook() {
        Book book = em.find(Book.class, 1L);
        List<Comment> expectedComments = book.getComments();

        List<Comment> actualComments = jpaCommentRepository.findByBookId(book.getId());

        assertThat(actualComments).usingRecursiveComparison().isEqualTo(expectedComments);
    }

    @DisplayName("Should update comment by id")
    @Test
    void shouldUpdateCommentById() {
        Comment comment = em.find(Comment.class, 1L);
        comment.setText("updated text");

        var actualComment = jpaCommentRepository.save(comment);

        assertThat(actualComment)
                .usingRecursiveComparison()
                .isEqualTo(comment);
    }
}