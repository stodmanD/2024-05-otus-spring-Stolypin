package com.example.accountprovider.security.handler.response;

import lombok.Getter;

@Getter
public enum ApiExceptionCode {

    UNEXPECTED_ERROR("The requested information could not be found. Please, check the provided details."),
    FILE_ALREADY_ATTACHED(""),
    FILE_NOT_FOUND(""),
    REQUEST_NOT_FOUND(""),
    SETTING_NOT_FOUND(""),
    TASK_NOT_FOUND(""),
    PDF_NOT_FOUND(""),
    COUNTERPARTY_NOT_FOUND(""),
    INVALID_FILE_FORMAT(""),
    INVALID_FILE_SIZE(""),
    INVALID_REQUEST(""),
    INVALID_REGISTER_STATUS(""),
    REQUEST_VALIDATION_FAILED(""),
    REQUEST_INVALID_STATUS(""),
    INVALID_SIGNATURE(""),
    UNAUTHORIZED(""),
    ACCESS_DENIED("Access to this resource is denied. Please, check your account permissions."),
    AUTHENTICATION_FAILED(""),
    USERNAME_NOT_FOUND_ERROR(""),
    ACCESS_REFUSED("");

    private final String defaultMessage;

    ApiExceptionCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}

