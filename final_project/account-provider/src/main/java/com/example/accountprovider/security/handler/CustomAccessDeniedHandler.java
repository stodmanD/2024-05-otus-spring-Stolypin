package com.example.accountprovider.security.handler;

import com.example.accountprovider.security.handler.response.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.example.accountprovider.security.handler.response.ApiExceptionCode.ACCESS_DENIED;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return;
        }

            log.error("403 Forbidden. Пользователю не разрешён доступ к ресурсу {}.",
                    request.getRequestURI());

        writeResponse(request, response);
    }


    private void writeResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        var exceptionResponse = new ExceptionResponse(
                ACCESS_DENIED,
                ACCESS_DENIED.getDefaultMessage(),
                request.getRequestURI()
        );
        response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionResponse));
    }
}