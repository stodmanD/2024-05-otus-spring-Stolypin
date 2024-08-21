package com.example.hw08mongo.repositories;

import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.models.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе SpringDataMongoDb для работы с комментариями ")
class CommentRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("должен загружать комментарий по id")
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

    @DisplayName("должен загружать список комментариев для книги")
    @Test
    void shouldReturnCorrectCommentsListForBook() {
        Book book = mongoTemplate.findAll(Book.class).get(0);
        Query query = new Query();
        query.addCriteria(Criteria.where("book").is(book));
        List<Comment> expectedComments = mongoTemplate.find(query, Comment.class);

        List<Comment> actualComments = commentRepository.findByBookId(book.getId());

        assertThat(actualComments).usingRecursiveComparison()
                .withComparatorForType(Comparator.comparing(Book::getId), Book.class)
                .isEqualTo(expectedComments);
    }

    @DisplayName("должен изменять текст комментария")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdateCommentById() {
        Comment comment = mongoTemplate.findAll(Comment.class).get(0);
        comment.setText("updated text");

        var actualComment = commentRepository.save(comment);

        assertThat(actualComment)
                .usingRecursiveComparison()
                .isEqualTo(comment);
    }

    @DisplayName("должен удалять комментарии для книги")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteCommentsForBook() {
        Book book = mongoTemplate.findAll(Book.class).get(0);
        Query query = new Query();
        query.addCriteria(Criteria.where("book").is(book));
        List<Comment> commentsBefore = mongoTemplate.find(query, Comment.class);

        commentRepository.deleteByBookId(book.getId());

        List<Comment> commentsAfter = mongoTemplate.find(query, Comment.class);
        assertThat(commentsBefore).hasSize(3);
        assertThat(commentsAfter).isEmpty();
    }
}