package com.example.hw17resilience4j.service;

import com.example.hw17resilience4j.exceptions.NotFoundException;
import com.example.hw17resilience4j.models.Book;
import com.example.hw17resilience4j.models.Comment;
import com.example.hw17resilience4j.repositories.BookRepository;
import com.example.hw17resilience4j.repositories.CommentRepository;
import com.example.hw17resilience4j.services.BookService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class CircuitBreakerTest {
    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private BookService bookService;

    @Test
    void findBookCircuitBreakerTest() {
        given(bookRepository.findById(anyLong())).willReturn(Optional.empty());
        given(commentRepository.findByBookId(anyLong())).willReturn(List.of(new Comment(4L, "comment text", new Book())));

        for (int i = 0; i < 5; i++) {
            assertThrows(NotFoundException.class, () -> bookService.findById(3));
        }

        for (int i = 0; i < 5; i++) {
            assertThrows(CallNotPermittedException.class, () -> bookService.findById(3));
        }

        verify(bookRepository, times(5)).findById(3L);
    }
}
