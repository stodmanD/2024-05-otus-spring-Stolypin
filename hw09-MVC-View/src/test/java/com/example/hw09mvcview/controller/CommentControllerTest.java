package com.example.hw09mvcview.controller;

import com.example.hw09mvcview.controllers.CommentController;
import com.example.hw09mvcview.dto.request.CommentCreateDto;
import com.example.hw09mvcview.dto.response.AuthorDto;
import com.example.hw09mvcview.dto.response.BookDto;
import com.example.hw09mvcview.dto.response.CommentDto;
import com.example.hw09mvcview.dto.response.GenreDto;
import com.example.hw09mvcview.services.AuthorService;
import com.example.hw09mvcview.services.BookService;
import com.example.hw09mvcview.services.CommentService;
import com.example.hw09mvcview.services.GenreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mvc;

    private BookDto bookDto;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;


    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @BeforeEach
    void init() {
        AuthorDto authorDto = new AuthorDto(1L, "author");
        GenreDto genreDto = new GenreDto(2L, "genre");
        bookDto = new BookDto(3L, "book", authorDto, List.of(genreDto), null);
    }

    @AfterEach
    void after() {
        verifyNoMoreInteractions(bookService, authorService, genreService);
    }

    @Test
    void shouldReturnCorrectAddCommentPage() throws Exception {
        given(bookService.findById(3L)).willReturn(bookDto);

        mvc.perform(get("/addComment?id=3"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("addComment"));

        verify(bookService).findById(3L);
    }

    @Test
    void shouldSaveNewComment() throws Exception {
        given(bookService.findById(3L)).willReturn(bookDto);
        given(commentService.create(anyLong(), anyString())).willReturn(new CommentDto(1L, "text"));

        mvc.perform(post("/addComment")
                        .flashAttr("book", bookDto)
                        .flashAttr("comment", new CommentCreateDto(0, "text")))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", bookDto))
                .andExpect(view().name("bookEdit"));

        verify(bookService).findById(3L);
        verify(commentService).create(anyLong(), anyString());
        verify(authorService).findAll();
        verify(genreService).findAll();
    }
}
