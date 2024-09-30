package com.example.hw09mvcview.controller;

import com.example.hw09mvcview.controllers.BookController;
import com.example.hw09mvcview.dto.request.BookCreateDto;
import com.example.hw09mvcview.dto.request.BookUpdateDto;
import com.example.hw09mvcview.dto.response.AuthorDto;
import com.example.hw09mvcview.dto.response.BookDto;
import com.example.hw09mvcview.dto.response.GenreDto;
import com.example.hw09mvcview.services.AuthorService;
import com.example.hw09mvcview.services.BookService;
import com.example.hw09mvcview.services.GenreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    private BookDto bookDto;

    private BookCreateDto bookCreateDto;

    private BookUpdateDto bookUpdateDto;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @BeforeEach
    void init() {
        AuthorDto authorDto = new AuthorDto(1L, "author");
        GenreDto genreDto = new GenreDto(2L, "genre");
        bookDto = new BookDto(3L, "book", authorDto, List.of(genreDto), null);
        bookCreateDto = new BookCreateDto(3L, "book", 1L, Set.of(2L));
        bookUpdateDto = new BookUpdateDto(3L, "book", 1L, Set.of(2L));
    }

    @AfterEach
    void after() {
        verifyNoMoreInteractions(bookService, authorService, genreService);
    }

    @Test
    void shouldReturnCorrectBookList() throws Exception {
        given(bookService.findAll()).willReturn(List.of(bookDto));

        mvc.perform(get("/"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("booksList"));

        verify(bookService).findAll();
    }

    @Test
    void shouldReturnCorrectBookEditPage() throws Exception {
        given(bookService.findById(3L)).willReturn(bookDto);

        mvc.perform(get("/book/3"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("bookEdit"));

        verify(bookService).findById(3L);
        verify(authorService).findAll();
        verify(genreService).findAll();
    }

    @Test
    void shouldSaveNewBook() throws Exception {
        bookDto.setId(0L);
        given(bookService.create(any())).willReturn(bookDto);

        mvc.perform(post("/book")
                        .flashAttr("book", bookCreateDto))
                .andExpect(status().isFound()).andDo(print())
                .andExpect(view().name("redirect:/"));

        verify(bookService).create(any());
    }

    @Test
    void shouldEditExistedBook() throws Exception {
        given(bookService.update(any())).willReturn(bookDto);

        mvc.perform(post("/book/3")
                        .flashAttr("book", bookUpdateDto))
                .andExpect(status().isFound()).andDo(print())
                .andExpect(view().name("redirect:/"));

        verify(bookService).update(any());
    }

    @Test
    void shouldReturnCorrectBookCreatePage() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("bookEdit"));

        verify(authorService).findAll();
        verify(genreService).findAll();
    }

    @Test
    void shouldDeleteBook() throws Exception {
        doNothing().when(bookService).deleteById(3L);

        mvc.perform(post("/book/3/delete")
                        .flashAttr("book", bookDto))
                .andExpect(status().isFound()).andDo(print())
                .andExpect(view().name("redirect:/"));

        verify(bookService).deleteById(3L);
    }
}
