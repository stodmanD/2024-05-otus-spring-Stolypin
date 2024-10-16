package com.example.hw15spring_boot_actuator.exceptions;

import com.example.hw15spring_boot_actuator.dto.response.ErrorDto;
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

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorDto> commonHandler(Exception e) {
        log.error("Server error: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ErrorDto(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}