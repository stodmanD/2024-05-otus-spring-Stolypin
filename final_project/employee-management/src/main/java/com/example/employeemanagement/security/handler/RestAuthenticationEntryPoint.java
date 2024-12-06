package com.example.employeemanagement.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.example.employeemanagement.security.handler.response.ApiExceptionCode;
import com.example.employeemanagement.security.handler.response.ExceptionResponse;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        var exceptionResponse = new ExceptionResponse(
                ApiExceptionCode.UNAUTHORIZED,
                ex.getMessage(),
                request.getRequestURI()
        );
        log.error("Произошла ошибка аутентификации:", ex);
        response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionResponse));
    }
}