package com.example.hw12spring_security_acl.controller;

import com.example.hw12spring_security_acl.controllers.api.BookRestController;
import com.example.hw12spring_security_acl.controllers.api.CommentRestController;
import com.example.hw12spring_security_acl.controllers.api.DictionaryRestController;
import com.example.hw12spring_security_acl.security.SecurityConfig;
import com.example.hw12spring_security_acl.services.AuthorService;
import com.example.hw12spring_security_acl.services.BookService;
import com.example.hw12spring_security_acl.services.CommentService;
import com.example.hw12spring_security_acl.services.GenreService;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({BookRestController.class, CommentRestController.class, DictionaryRestController.class})
@Import({SecurityConfig.class})
public class AuthenticationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;


    @DisplayName("Should return expected status")
    @ParameterizedTest(name = "{0} {1} for user {2} with password {3} should return {4} status")
    @MethodSource("getTestData")
    void shouldReturnExpectedStatus(String method, String url, String userName, String password, String role, int status, boolean checkLoginRedirection, String body) throws Exception {
        var request = method2RequestBuilder(method, url, body);
        if (Objects.nonNull(userName)) {
            request = request.with(user(userName).password(password).authorities().roles(role));
        }
        ResultActions resultActions = mvc.perform(request).andExpect(status().is(status));
        if (checkLoginRedirection) {
            resultActions.andExpect(redirectedUrlPattern("**/login"));
        }
    }

    private MockHttpServletRequestBuilder method2RequestBuilder(String method, String url, String request) throws JSONException {
        Map<String, Function<String, MockHttpServletRequestBuilder>> methodMap =
                Map.of("get", MockMvcRequestBuilders::get,
                        "post", MockMvcRequestBuilders::post,
                        "put", MockMvcRequestBuilders::put,
                        "delete", MockMvcRequestBuilders::delete);
        return methodMap.get(method).apply(url).content(request).contentType(MediaType.APPLICATION_JSON);
    }


    public static Stream<Arguments> getTestData() {

        return Stream.of(
                Arguments.of("get", "/api/book", "admin", "password", "ADMIN", 200, false, "")
                , Arguments.of("get", "/api/book", null, null, "ADMIN", 302, true, "")
                , Arguments.of("get", "/api/book", "user", "password", "USER", 200, false, "")

                , Arguments.of("post", "/api/book", "user", "password", "ADMIN", 200, false, "{\"id\": \"\", \"title\": \"wqeqw\", \"authorId\": \"1\", \"genreIds\": [\"1\", \"2\", \"3\"]}")
                , Arguments.of("post", "/api/book", null, null, "ADMIN", 302, true, "")

                , Arguments.of("get", "/api/book/3", "user", "password", "ADMIN", 200, false, "")
                , Arguments.of("get", "/api/book/3", null, null, "ADMIN", 302, true, "")

                , Arguments.of("put", "/api/book/3", "user", "password", "ADMIN", 200, false, "{\"id\": \"3\", \"title\": \"wqeqw\", \"authorId\": \"1\", \"genreIds\": [\"1\", \"2\", \"3\"]}")
                , Arguments.of("put", "/api/book/3", null, null, "ADMIN", 302, true, "")

                , Arguments.of("delete", "/api/book/3", "admin", "password", "ADMIN", 200, false, "")
                , Arguments.of("delete", "/api/book/3", null, null, "ADMIN", 302, true, "")

                , Arguments.of("get", "/api/comment/3", "user", "password", "ADMIN", 200, false, "")
                , Arguments.of("get", "/api/comment/3", null, null, "ADMIN", 302, true, "")

                , Arguments.of("post", "/api/comment/3", "user", "password", "ADMIN", 200, false, "{\"text\": \"xcxc\"}")
                , Arguments.of("post", "/api/comment/3", null, null, "ADMIN", 302, true, "")

                , Arguments.of("get", "/api/dic/genres", "user", "password", "ADMIN", 200, false, "")
                , Arguments.of("get", "/api/dic/genres", null, null, "ADMIN", 302, true, "")
                , Arguments.of("get", "/api/dic/genres", "user", "password", "USER", 403, false, "")
                , Arguments.of("get", "/api/dic/authors", "user", "password", "ADMIN", 200, false, "")
                , Arguments.of("get", "/api/dic/authors", null, null, "ADMIN", 302, true, "")
        );
    }
}
