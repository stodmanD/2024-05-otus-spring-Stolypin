package com.example.hw17resilience4j.exceptions;

import com.example.hw17resilience4j.dto.response.ErrorDto;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorDto> notFoundException(NotFoundException e) {
        log.error("Object not found: {}", e.getMessage());
        return new ResponseEntity<>(new ErrorDto(e.toString()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    ResponseEntity<ErrorDto> circuitBreakerException(CallNotPermittedException e) {
        log.error("Service unavailable: {}", e.getMessage());
        return new ResponseEntity<>(new ErrorDto(e.toString()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorDto> commonHandler(Exception e) {
        log.error("Server error: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ErrorDto(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}