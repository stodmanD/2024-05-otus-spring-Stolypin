package com.example.accountprovider.security.handler.response;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class ExceptionResponse {

    private final String timestamp;

    private final String code;

    private final String error;

    private final String path;

    public ExceptionResponse(ApiExceptionCode code, String error, String path) {
        this.timestamp = LocalDateTime.now().toString();
        this.code = code.name();
        this.error = error;
        this.path = path;
    }
}