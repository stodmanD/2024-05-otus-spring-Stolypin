package com.example.hw18webflux.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import com.example.hw18webflux.dto.response.ErrorDto;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDto notFoundException(NotFoundException e) {
        log.error("Object not found: {}", e.getMessage());
        return new ErrorDto(e.toString());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto validationException(WebExchangeBindException e) {
        log.error("Object not found: {}", e.getMessage());
        return new ErrorDto(e.toString());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorDto commonHandler(Exception e) {
        log.error("Server error: {}", e.getMessage(), e);
        return new ErrorDto(e.toString());
    }
}