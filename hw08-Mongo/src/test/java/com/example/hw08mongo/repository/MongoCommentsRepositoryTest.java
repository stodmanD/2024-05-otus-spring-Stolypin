package com.example.hw08mongo.repository;

import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.models.Comment;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий mongo для работы  с комментариями")
@DataMongoTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MongoCommentsRepositoryTest {

    private static final int COUNT_COMMENTS_OF_FIRST_BOOK = 2;

    private static final String FIRST_COMM_FIRST_BOOK = "Comment_Book1_ 1";

    private static final String SECOND_COMM_FIRST_BOOK = "Comment_Book1_ 2";

    private static final String NEW_COMMENT_STRING = "new_comment";

    private Book firstBook;


    private List<Book> books;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void init() {
        books = mongoTemplate.findAll(Book.class).stream()
                .filter(b -> b.getTitle().contains("Comment_")).toList();
        firstBook = books.stream()
                .filter(b -> b.getTitle().equals("Comment_TTL_1")).findFirst().get();
    }

    @DisplayName("Должен загружать список всех коментариев к книге")
    @Test
    @Order(1)
    void shouldReturnCorrectCommentListByBookId() {
        List<Comment> comments = commentRepository.getAllCommentsByBookId(firstBook.getId());
        assertThat(comments).isNotNull().hasSize(COUNT_COMMENTS_OF_FIRST_BOOK)
                .allMatch(s -> !s.getText().equals(""))
                .anyMatch(s -> s.getText().equals(FIRST_COMM_FIRST_BOOK))
                .anyMatch(s -> s.getText().equals(SECOND_COMM_FIRST_BOOK));
    }

    @DisplayName("Должен загружать коментарий по id")
    @Test
    @Order(2)
    void shouldReturnCorrectCommentById() {
        Comment secondComment = getComment(SECOND_COMM_FIRST_BOOK);
        Optional<Comment> optionalComment = commentRepository.findById(secondComment.getId());
        assertThat(optionalComment).isPresent().get()
                .matches(s -> s.getText().equals(SECOND_COMM_FIRST_BOOK)
                        && s.getBook().getId().equals(firstBook.getId()));
    }


    @DisplayName("Должен вставлять новый коментарий")
    @Test
    @Order(3)
    void shouldInsertNewComment() {
        Comment commentForInsert = new Comment(NEW_COMMENT_STRING, firstBook);
        commentRepository.save(commentForInsert);
        Comment newComment = commentRepository.save(commentForInsert);
        assertThat(newComment)
                .matches(s -> s.getBook().getId().equals(firstBook.getId())&&
                        s.getText().equals(NEW_COMMENT_STRING));
    }

    @DisplayName("Должен обновлять коментарий по id")
    @Test
    @Order(4)
    void shouldUpdateCommentById() {
        Comment secondComment = getComment(SECOND_COMM_FIRST_BOOK);
        Comment comment = new Comment(secondComment.getId(), NEW_COMMENT_STRING, firstBook);
        Comment updatedComment = commentRepository.save(comment);
        Comment commentAfterUpdate = commentRepository.findById(updatedComment.getId()).get();
        assertThat(commentAfterUpdate)
                .matches(s -> s.getId().equals(secondComment.getId()) &&
                        s.getText().equals(NEW_COMMENT_STRING) &&
                        s.getBook().getId().equals(firstBook.getId()));

    }

    @DisplayName("Должен удалять коментарий по id")
    @Test
    @Order(5)
    void shouldDeleteCommentById() {
        Comment comment = getComment(FIRST_COMM_FIRST_BOOK);
        commentRepository.deleteById(comment.getId());
        Optional<Comment> deletedComment = commentRepository.findById(comment.getId());
        assertThat(deletedComment).isNotPresent();
    }

    private Comment getComment(String text) {
        return  commentRepository.findAll().stream()
                .filter(c -> c.getText().equals(text)).findFirst().get();
    }
}
