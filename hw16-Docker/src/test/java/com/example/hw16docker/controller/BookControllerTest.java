package com.example.hw16docker.controller;

import com.example.hw16docker.controllers.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnCorrectBookListPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("booksList"));
    }

    @Test
    void shouldReturnCorrectBookEditPage() throws Exception {
        mvc.perform(get("/book/3"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("bookEdit"));
    }

    @Test
    void shouldReturnCorrectBookCreatePage() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("bookEdit"));
    }
}
