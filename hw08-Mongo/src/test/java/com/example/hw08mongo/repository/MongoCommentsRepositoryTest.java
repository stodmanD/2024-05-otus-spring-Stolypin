package com.example.hw08mongo.repository;

import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.models.Comment;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий mongo для работы  с комментариями")
@DataMongoTest
public class MongoCommentsRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("Должен загружать список всех комментариев к книге")
    @Test
    void shouldReturnCorrectCommentListByBookId() {
        Book book = mongoTemplate.findAll(Book.class).get(0);
        Query query = new Query();
        query.addCriteria(Criteria.where("book").is(book));
        List<Comment> expectedComments = mongoTemplate.find(query, Comment.class);

        List<Comment> actualComments = commentRepository.getAllCommentsByBookId(book.getId());

        assertThat(actualComments).usingRecursiveComparison()
                .withComparatorForType(Comparator.comparing(Book::getId), Book.class)
                .isEqualTo(expectedComments);
    }

    @DisplayName("Должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        Comment expectedComment = mongoTemplate.findAll(Comment.class).get(0);
        var actualComment = commentRepository.findById(expectedComment.getId());
        assertThat(actualComment).isPresent()
                .get()
                .usingRecursiveComparison()
                .withComparatorForType(Comparator.comparing(Book::getId), Book.class)
                .isEqualTo(expectedComment);
    }

    @DisplayName("Должен вставлять новый комментарий")
    @Test
    void shouldInsertNewComment() {
        Book book = mongoTemplate.findAll(Book.class).get(0);
        String NEW_COMMENT_STRING = "new_comment";
        Comment commentForInsert = new Comment(NEW_COMMENT_STRING, book);
        Comment newComment = commentRepository.save(commentForInsert);
        assertThat(newComment)
                .matches(s -> s.getBook().getId().equals(book.getId())&&
                        s.getText().equals(NEW_COMMENT_STRING));
    }

    @DisplayName("Должен обновлять комментарий по id")
    @Test
    void shouldUpdateCommentById() {
        Comment comment = mongoTemplate.findAll(Comment.class).get(0);
        comment.setText("updated text");
        var actualComment = commentRepository.save(comment);
        assertThat(actualComment)
                .usingRecursiveComparison()
                .isEqualTo(comment);

    }

    @DisplayName("Должен удалять комментарий по id")
    @Test
    void shouldDeleteCommentById() {
        Comment comment= mongoTemplate.findAll(Comment.class).get(0);
        commentRepository.deleteById(comment.getId());
        assertThat(mongoTemplate.findById(comment.getId(), Comment.class)).isNull();
    }
}
