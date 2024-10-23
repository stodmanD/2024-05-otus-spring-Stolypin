package com.example.hw16docker.health;

import com.example.hw16docker.actuators.BooksHealthIndicator;
import com.example.hw16docker.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

public class BooksHealthIndicatorTest {
    private static final String UP_MESSAGE = "Everything is good, so far)";
    private static final String DOWN_MESSAGE = "Book storage is empty";

    private BookRepository bookRepository;
    private BooksHealthIndicator booksHealthIndicator;

    @BeforeEach
    void init() {
        bookRepository = Mockito.mock(BookRepository.class);
        booksHealthIndicator = new BooksHealthIndicator(bookRepository);
    }

    @Test
    void positiveTest() {
        when(bookRepository.count()).thenReturn(2L);

        Health result = booksHealthIndicator.health();

        assertAll(
                () -> assertEquals(Status.UP, result.getStatus()),
                () -> assertEquals(UP_MESSAGE, result.getDetails().get("message"))
        );
    }

    @Test
    void negativeTest() {
        when(bookRepository.count()).thenReturn(0L);

        Health result = booksHealthIndicator.health();

        assertAll(
                () -> assertEquals(Status.DOWN, result.getStatus()),
                () -> assertEquals(DOWN_MESSAGE, result.getDetails().get("message"))
        );
    }

    @Test
    void exceptionTest() {
        when(bookRepository.count()).thenThrow(new RuntimeException());

        Health result = booksHealthIndicator.health();

        assertAll(
                () -> assertEquals(Status.DOWN, result.getStatus()),
                () -> assertEquals(DOWN_MESSAGE, result.getDetails().get("message"))
        );
    }
}
