package com.example.hw09mvcview.repositories;

import com.example.hw09mvcview.models.Comment;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе SpringDataJpa для работы с комментариями ")
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        Comment expectedComment = em.find(Comment.class, 1L);

        var actualComment = commentRepository.findById(expectedComment.getId());

        assertThat(actualComment).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать список комментариев для книги")
    @Test
    void shouldReturnCorrectCommentsListForBook() {
        TypedQuery<Comment> query = em.getEntityManager()
                .createQuery("select c from Comment c where c.book.id = ?1", Comment.class);
        query.setParameter(1, 1L);
        List<Comment> expectedComments = query.getResultList();

        List<Comment> actualComments = commentRepository.findByBookId(1L);

        assertThat(actualComments).usingRecursiveComparison().isEqualTo(expectedComments);
    }

    @DisplayName("должен изменять текст комментария")
    @Test
    void shouldUpdateCommentById() {
        Comment comment = em.find(Comment.class, 1L);
        comment.setText("updated text");

        var actualComment = commentRepository.save(comment);

        assertThat(actualComment)
                .usingRecursiveComparison()
                .isEqualTo(comment);
    }

    @DisplayName("должен удалять комментарии для книги")
    @Test
    void shouldDeleteCommentsForBook() {
        List<Comment> commentsBefore = getComments();

        commentRepository.deleteByBookId(1L);

        List<Comment> commentsAfter = getComments();
        assertThat(commentsBefore).hasSize(3);
        assertThat(commentsAfter).isEmpty();
    }

    private List<Comment> getComments() {
        List<Comment> result = new LinkedList<>();
        for (int i = 1; i < 4; i++) {
            Optional.ofNullable(em.find(Comment.class, i)).ifPresent(result::add);
        }
        return result;
    }
}