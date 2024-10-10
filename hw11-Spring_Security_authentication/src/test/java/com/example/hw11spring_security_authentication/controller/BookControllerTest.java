package com.example.hw11spring_security_authentication.controller;

import com.example.hw11spring_security_authentication.controllers.BookController;
import com.example.hw11spring_security_authentication.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
@Import({SecurityConfig.class})
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "user")
    void shouldReturnCorrectBookListPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("booksList"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnCorrectBookEditPage() throws Exception {
        mvc.perform(get("/book/3"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("bookEdit"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldReturnCorrectBookCreatePage() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("bookEdit"));
    }
}
