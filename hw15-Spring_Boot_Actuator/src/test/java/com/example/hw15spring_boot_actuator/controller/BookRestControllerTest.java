package com.example.hw15spring_boot_actuator.controller;

import com.example.hw15spring_boot_actuator.controllers.api.BookRestController;
import com.example.hw15spring_boot_actuator.dto.request.BookCreateDto;
import com.example.hw15spring_boot_actuator.dto.request.BookUpdateDto;
import com.example.hw15spring_boot_actuator.dto.response.AuthorDto;
import com.example.hw15spring_boot_actuator.dto.response.BookDto;
import com.example.hw15spring_boot_actuator.dto.response.CommentDto;
import com.example.hw15spring_boot_actuator.dto.response.GenreDto;
import com.example.hw15spring_boot_actuator.exceptions.NotFoundException;
import com.example.hw15spring_boot_actuator.services.AuthorService;
import com.example.hw15spring_boot_actuator.services.BookService;
import com.example.hw15spring_boot_actuator.services.CommentService;
import com.example.hw15spring_boot_actuator.services.GenreService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    private BookDto bookDto;

    private BookCreateDto bookCreateDto;

    private BookUpdateDto bookUpdateDto;

    private CommentDto commentDto;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @BeforeEach
    void init() {
        AuthorDto authorDto = new AuthorDto(1L, "author");
        GenreDto genreDto = new GenreDto(2L, "genre");
        bookDto = new BookDto(3L, "book", authorDto, List.of(genreDto), null);
        bookCreateDto = new BookCreateDto("book", 1L, Set.of(2L));
        bookUpdateDto = new BookUpdateDto(3L, "book", 1L, Set.of(2L));
        commentDto = new CommentDto(4L, "comment text");
    }

    @AfterEach
    void after() {
        verifyNoMoreInteractions(bookService, authorService, genreService, commentService);
    }

    @Test
    void getBookListPositiveTest() throws Exception {
        List<BookDto> daoRes = List.of(this.bookDto);
        given(bookService.findAll()).willReturn(daoRes);

        MvcResult mvcResult = mvc.perform(get("/api/book"))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();
        List<BookDto> response = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        verify(bookService).findAll();
        assertEquals(daoRes, response);
    }

    @Test
    void getBookListError500Test() throws Exception {
        given(bookService.findAll()).willThrow(RuntimeException.class);

        mvc.perform(get("/api/book"))
                .andExpect(status().isInternalServerError()).andDo(print());

        verify(bookService).findAll();
    }

    @Test
    void saveNewBookPositiveTest() throws Exception {
        given(bookService.create(any())).willReturn(bookDto);

        MvcResult mvcResult = mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(bookCreateDto)))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();

        BookDto response = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), BookDto.class);
        verify(bookService).create(bookCreateDto);
        assertEquals(bookDto, response);
    }

    @Test
    void saveNewBookError500Test() throws Exception {
        given(bookService.create(any())).willThrow(RuntimeException.class);

        mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(bookCreateDto)))
                .andExpect(status().isInternalServerError()).andDo(print());
        ;

        verify(bookService).create(bookCreateDto);
    }

    @Test
    void editBookPositiveTest() throws Exception {
        given(bookService.update(any())).willReturn(bookDto);

        MvcResult mvcResult = mvc.perform(put("/api/book/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();

        BookDto response = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), BookDto.class);
        verify(bookService).update(any());
        assertEquals(bookDto, response);
    }

    @Test
    void editBookError404Test() throws Exception {
        given(bookService.update(any())).willThrow(NotFoundException.class);

        mvc.perform(put("/api/book/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isNotFound()).andDo(print());

        verify(bookService).update(any());
    }

    @Test
    void editBookError500Test() throws Exception {
        given(bookService.update(any())).willThrow(RuntimeException.class);

        mvc.perform(put("/api/book/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isInternalServerError()).andDo(print());

        verify(bookService).update(any());
    }

    @Test
    void findBookPositiveTest() throws Exception {
        BookDto daoRes = this.bookDto;
        given(bookService.findById(anyLong())).willReturn(daoRes);

        MvcResult mvcResult = mvc.perform(get("/api/book/3"))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();

        BookDto response = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), BookDto.class);
        verify(bookService).findById(3L);
        assertEquals(daoRes, response);
    }

    @Test
    void findBookError404Test() throws Exception {
        given(bookService.findById(anyLong())).willThrow(NotFoundException.class);

        mvc.perform(get("/api/book/3"))
                .andExpect(status().isNotFound()).andDo(print());

        verify(bookService).findById(3L);
    }

    @Test
    void findBookError500Test() throws Exception {
        given(bookService.findById(anyLong())).willThrow(RuntimeException.class);

        mvc.perform(get("/api/book/3"))
                .andExpect(status().isInternalServerError()).andDo(print());

        verify(bookService).findById(3L);
    }

    @Test
    void deleteBookPositiveTest() throws Exception {
        doNothing().when(bookService).deleteById(3L);

        mvc.perform(delete("/api/book/3"))
                .andExpect(status().isOk()).andDo(print());

        verify(bookService).deleteById(3L);
    }

    @Test
    void deleteBookError500Test() throws Exception {
        doThrow(RuntimeException.class).when(bookService).deleteById(3L);

        mvc.perform(delete("/api/book/3"))
                .andExpect(status().isInternalServerError()).andDo(print());

        verify(bookService).deleteById(3L);
    }

}
