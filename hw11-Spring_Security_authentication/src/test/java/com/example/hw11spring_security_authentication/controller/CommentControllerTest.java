package com.example.hw11spring_security_authentication.controller;

import com.example.hw11spring_security_authentication.controllers.CommentController;
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

@WebMvcTest(CommentController.class)
@Import({SecurityConfig.class})
public class CommentControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "user")
    void shouldReturnCorrectAddCommentPage() throws Exception {
        mvc.perform(get("/addComment?id=3"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("addComment"));
    }
}
