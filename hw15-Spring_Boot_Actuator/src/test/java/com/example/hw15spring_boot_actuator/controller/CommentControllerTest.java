package com.example.hw15spring_boot_actuator.controller;

import com.example.hw15spring_boot_actuator.controllers.CommentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnCorrectAddCommentPage() throws Exception {
        mvc.perform(get("/addComment?id=3"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(view().name("addComment"));
    }
}
