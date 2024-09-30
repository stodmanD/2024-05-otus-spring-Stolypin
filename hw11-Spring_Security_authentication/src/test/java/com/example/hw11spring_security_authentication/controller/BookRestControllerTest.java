package com.example.hw11spring_security_authentication.controller;

import com.example.hw11spring_security_authentication.controllers.api.BookRestController;
import com.example.hw11spring_security_authentication.dto.request.BookCreateDto;
import com.example.hw11spring_security_authentication.dto.request.BookUpdateDto;
import com.example.hw11spring_security_authentication.dto.response.AuthorDto;
import com.example.hw11spring_security_authentication.dto.response.BookDto;
import com.example.hw11spring_security_authentication.dto.response.CommentDto;
import com.example.hw11spring_security_authentication.dto.response.GenreDto;
import com.example.hw11spring_security_authentication.exceptions.NotFoundException;
import com.example.hw11spring_security_authentication.security.SecurityConfig;
import com.example.hw11spring_security_authentication.services.AuthorService;
import com.example.hw11spring_security_authentication.services.BookService;
import com.example.hw11spring_security_authentication.services.CommentService;
import com.example.hw11spring_security_authentication.services.GenreService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookRestController.class)
@Import({SecurityConfig.class})
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
    @WithMockUser(username = "user")
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
    @WithMockUser(username = "user")
    void getBookListError500Test() throws Exception {
        given(bookService.findAll()).willThrow(RuntimeException.class);

        mvc.perform(get("/api/book"))
                .andExpect(status().isInternalServerError()).andDo(print());

        verify(bookService).findAll();
    }

    @Test
    @WithMockUser(username = "user")
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
    @WithMockUser(username = "user")
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
    @WithMockUser(username = "user")
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
    @WithMockUser(username = "user")
    void editBookError404Test() throws Exception {
        given(bookService.update(any())).willThrow(NotFoundException.class);

        mvc.perform(put("/api/book/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isNotFound()).andDo(print());

        verify(bookService).update(any());
    }

    @Test
    @WithMockUser(username = "user")
    void editBookError500Test() throws Exception {
        given(bookService.update(any())).willThrow(RuntimeException.class);

        mvc.perform(put("/api/book/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isInternalServerError()).andDo(print());

        verify(bookService).update(any());
    }

    @Test
    @WithMockUser(username = "user")
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
    @WithMockUser(username = "user")
    void findBookError404Test() throws Exception {
        given(bookService.findById(anyLong())).willThrow(NotFoundException.class);

        mvc.perform(get("/api/book/3"))
                .andExpect(status().isNotFound()).andDo(print());

        verify(bookService).findById(3L);
    }

    @Test
    @WithMockUser(username = "user")
    void findBookError500Test() throws Exception {
        given(bookService.findById(anyLong())).willThrow(RuntimeException.class);

        mvc.perform(get("/api/book/3"))
                .andExpect(status().isInternalServerError()).andDo(print());

        verify(bookService).findById(3L);
    }

    @Test
    @WithMockUser(username = "user")
    void deleteBookPositiveTest() throws Exception {
        doNothing().when(bookService).deleteById(3L);

        mvc.perform(delete("/api/book/3"))
                .andExpect(status().isOk()).andDo(print());

        verify(bookService).deleteById(3L);
    }

    @Test
    @WithMockUser(username = "user")
    void deleteBookError500Test() throws Exception {
        doThrow(RuntimeException.class).when(bookService).deleteById(3L);

        mvc.perform(delete("/api/book/3"))
                .andExpect(status().isInternalServerError()).andDo(print());

        verify(bookService).deleteById(3L);
    }

    @Test
    void editExistedBookUnauthorizedTest() throws Exception {
        mvc.perform(post("/book/3")
                        .flashAttr("book", bookUpdateDto))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/login"))
                .andDo(print());
    }

    @Test
    void BookEditPageUnauthorizedTest() throws Exception {
        mvc.perform(get("/book/3"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/login"))
                .andDo(print());
    }

    @Test
    void saveNewBookUnauthorizedTest() throws Exception {
        mvc.perform(post("/book")
                        .flashAttr("book", bookCreateDto))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/login"))
                .andDo(print());
    }

    @Test
    void bookCreatePageUnauthorizedTest() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/login"))
                .andDo(print());
    }

    @Test
    void bookListUnauthorizedTest() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/login"))
                .andDo(print());
    }
    @Test
    void deleteBookUnauthorizedTest() throws Exception {
        mvc.perform(post("/book/3/delete")
                        .flashAttr("book", bookDto))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/login"))
                .andDo(print());
    }

}
