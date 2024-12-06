package com.example.accountprovider.service;

import com.example.accountprovider.dto.AccountDto;
import com.example.accountprovider.exception.ValidationException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ValidatorService {

    private static final String PART_OF_LOGIN_REGEXP = "[a-z]+";

    private static final String PASSWORD_REGEXP =
            "/(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}/g";

    private static final String EMAIL_REGEXP = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";



    public static void checkUuid(String uuid) {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid UUID: " + uuid);
        }
    }

    public static void checkLogin(String login) {
        List<String> validPartsOfLogin = Arrays.stream(login.split("\\."))
                .filter(part -> part.matches(PART_OF_LOGIN_REGEXP))
                .filter(part -> part.length() > 2 && part.length() < 50)
                .toList();
        if (validPartsOfLogin.size() < 2) {
            throw new ValidationException("Invalid login: " + login);
        }
    }

    public static void checkPassword(String password) {
        if (!password.matches(PASSWORD_REGEXP)) {
            throw new ValidationException("Invalid password: " + password);
        }
    }

    public static void checkEmail(String email) {
        if (!email.matches(EMAIL_REGEXP)) {
            throw new ValidationException("Invalid email: " + email);
        }
    }

    public static void checkExpireDate(LocalDate expireDate) {
        if (expireDate.isBefore(LocalDate.now())) {
            throw new ValidationException("Invalid expire date! Date cannot be in the past: " + expireDate);
        }
    }

    public static void checkAccount(AccountDto accountDto) {
        if (accountDto.id() != null) {
            checkUuid(accountDto.id());
        }
        checkLogin(accountDto.login());
        checkPassword(accountDto.password());
        checkEmail(accountDto.email());
        checkExpireDate(accountDto.dateTillPassword());
    }

}
