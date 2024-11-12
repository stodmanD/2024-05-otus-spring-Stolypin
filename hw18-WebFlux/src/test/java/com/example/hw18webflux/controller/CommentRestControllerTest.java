package com.example.hw18webflux.controller;


import com.example.hw18webflux.dto.request.CommentCreateDto;
import com.example.hw18webflux.dto.response.CommentDto;
import com.example.hw18webflux.dto.response.ErrorDto;
import com.example.hw18webflux.models.Author;
import com.example.hw18webflux.models.Book;
import com.example.hw18webflux.models.Comment;
import com.example.hw18webflux.models.Genre;
import com.example.hw18webflux.repositories.BookRepository;
import com.example.hw18webflux.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureWebTestClient
public class CommentRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    private CommentDto commentDto;

    private Comment comment;

    private Book book;
    @BeforeEach
    void init() {
        Author author = new Author("1", "author");
        Genre genre = new Genre("2", "genre");
        book = new Book("3", "book", author, List.of(genre));
        commentDto = new CommentDto("4", "comment text");
        comment = new Comment("4", "comment text", this.book);
    }

    @Test
    void getCommentsForBookPositiveTest() {
        given(commentRepository.findByBookId(anyString())).willReturn(Flux.just(this.comment));

        webClient.get()
                .uri("/api/comment/3")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .hasSize(1)
                .contains(this.commentDto);

        verify(commentRepository).findByBookId("3");
    }

    @Test
    void getCommentsForBookError500Test() {
        given(commentRepository.findByBookId(anyString())).willThrow(RuntimeException.class);

        webClient.get()
                .uri("/api/comment/3")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ErrorDto.class);

        verify(commentRepository).findByBookId("3");
    }

    @Test
    void addNewCommentForBookPositiveTest() {
        given(bookRepository.findById(anyString())).willReturn(Mono.just(this.book));
        given(commentRepository.save(any())).willReturn(Mono.just(this.comment));
        CommentCreateDto request = new CommentCreateDto("comment text");

        webClient.post()
                .uri("/api/comment/3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDto.class)
                .isEqualTo(this.commentDto);

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(bookRepository).findById("3");
        verify(commentRepository).save(captor.capture());
        assertThat(captor.getValue()).usingRecursiveComparison()
                .isEqualTo(new Comment(null, "comment text", this.book));
    }

    @Test
    void addNewCommentForBookError404Test() {
        given(bookRepository.findById(anyString())).willReturn(Mono.empty());
        given(commentRepository.save(any())).willReturn(Mono.just(this.comment));
        CommentCreateDto request = new CommentCreateDto("comment text");

        webClient.post()
                .uri("/api/comment/3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isNotFound();

        verify(bookRepository).findById("3");
    }

    @Test
    void addNewCommentForBookError500Test() {
        given(bookRepository.findById(anyString())).willReturn(Mono.just(this.book));
        given(commentRepository.save(any())).willThrow(RuntimeException.class);
        CommentCreateDto request = new CommentCreateDto("comment text");

        webClient.post()
                .uri("/api/comment/3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().is5xxServerError();

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(bookRepository).findById("3");
        verify(commentRepository).save(captor.capture());
        assertThat(captor.getValue()).usingRecursiveComparison()
                .isEqualTo(new Comment(null, "comment text", this.book));
    }

    @Test
    void addNewCommentForBookBadRequestTest() {
        webClient.post()
                .uri("/api/comment/3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new CommentCreateDto()))
                .exchange()
                .expectStatus().isBadRequest();
    }
}
