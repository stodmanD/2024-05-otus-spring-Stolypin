package com.example.hw17resilience4j.controller;


import com.example.hw17resilience4j.controllers.api.CommentRestController;
import com.example.hw17resilience4j.dto.request.CommentCreateDto;
import com.example.hw17resilience4j.dto.response.CommentDto;
import com.example.hw17resilience4j.exceptions.NotFoundException;
import com.example.hw17resilience4j.services.CommentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentRestController.class)
public class CommentRestControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    private CommentDto commentDto;


    @MockBean
    private CommentService commentService;

    @BeforeEach
    void init() {
        commentDto = new CommentDto(4L, "comment text");
    }
    @Test
    void getCommentsForBookPositiveTest() throws Exception {
        List<CommentDto> daoRes = List.of(this.commentDto);
        given(commentService.findByBookId(anyLong())).willReturn(daoRes);

        MvcResult mvcResult = mvc.perform(get("/api/comment/3"))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();
        List<CommentDto> response = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});

        verify(commentService).findByBookId(3L);
        assertEquals(daoRes, response);
    }

    @Test
    void getCommentsForBookError500Test() throws Exception {
        given(commentService.findByBookId(anyLong())).willThrow(RuntimeException.class);

        mvc.perform(get("/api/comment/3"))
                .andExpect(status().isInternalServerError()).andDo(print())
                .andReturn();

        verify(commentService).findByBookId(3L);
    }

    @Test
    void addNewCommentForBookPositiveTest() throws Exception {
        given(commentService.create(anyLong(), anyString())).willReturn(commentDto);

        CommentCreateDto request = new CommentCreateDto("comment text");
        MvcResult mvcResult = mvc.perform(post("/api/comment/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(request)))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();

        CommentDto response = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), CommentDto.class);
        verify(commentService).create(3L, request.getText());
        assertEquals(commentDto, response);
    }

    @Test
    void addNewCommentForBookError404Test() throws Exception {
        given(commentService.create(anyLong(), anyString())).willThrow(NotFoundException.class);

        CommentCreateDto request = new CommentCreateDto("comment text");
        mvc.perform(post("/api/comment/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(request)))
                .andExpect(status().isNotFound()).andDo(print())
                .andReturn();

        verify(commentService).create(3L, request.getText());
    }

    @Test
    void addNewCommentForBookTest() throws Exception {
        given(commentService.create(anyLong(), anyString())).willThrow(RuntimeException.class);

        CommentCreateDto request = new CommentCreateDto("comment text");
        mvc.perform(post("/api/comment/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(request)))
                .andExpect(status().isInternalServerError()).andDo(print())
                .andReturn();

        verify(commentService).create(3L, request.getText());
    }
}

