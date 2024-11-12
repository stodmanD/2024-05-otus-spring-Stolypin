package com.example.hw18webflux.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import com.example.hw18webflux.models.Book;
import com.example.hw18webflux.models.Comment;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе ReactiveMongoRepository для работы с комментариями ")
class CommentRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        Comment expectedComment = mongoTemplate.findAll(Comment.class).get(0);

        Mono<Comment> commentMono = commentRepository.findById(expectedComment.getId());

        StepVerifier
                .create(commentMono)
                .assertNext(comment -> assertThat(comment)
                        .usingRecursiveComparison()
                        .withComparatorForType(Comparator.comparing(Book::getId), Book.class)
                        .isEqualTo(expectedComment))
                .expectComplete()
                .verify();
    }

    @DisplayName("должен загружать список комментариев для книги")
    @Test
    void shouldReturnCorrectCommentsListForBook() {
        Book book = mongoTemplate.findAll(Book.class).get(0);
        Query query = new Query();
        query.addCriteria(Criteria.where("book").is(book));
        List<Comment> expectedComments = mongoTemplate.find(query, Comment.class);

        Flux<Comment> commentFlux = commentRepository.findByBookId(book.getId());

        List<Comment> actualComments = commentFlux.toStream().toList();
        assertThat(actualComments).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .withComparatorForType(Comparator.comparing(Book::getId), Book.class)
                .isEqualTo(expectedComments);
    }

    @DisplayName("должен изменять текст комментария")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldUpdateCommentById() {
        Comment comment = mongoTemplate.findAll(Comment.class).get(0);
        comment.setText("updated text");

        Mono<Comment> commentMono = commentRepository.save(comment);

        StepVerifier
                .create(commentMono)
                .assertNext(actualComment -> assertThat(actualComment)
                        .usingRecursiveComparison()
                        .isEqualTo(comment))
                .expectComplete()
                .verify();
    }

    @DisplayName("должен удалять комментарии для книги")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteCommentsForBook() {
        Book book = mongoTemplate.findAll(Book.class).get(0);
        Query query = new Query();
        query.addCriteria(Criteria.where("book").is(book));
        List<Comment> commentsBefore = mongoTemplate.find(query, Comment.class);

        Mono<Void> voidMono = commentRepository.deleteByBookId(book.getId());
        StepVerifier.create(voidMono)
                .expectComplete()
                .verify();

        List<Comment> commentsAfter = mongoTemplate.find(query, Comment.class);
        assertThat(commentsBefore).hasSize(3);
        assertThat(commentsAfter).isEmpty();
    }
}