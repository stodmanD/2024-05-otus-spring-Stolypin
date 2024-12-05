package com.example.accountprovider.dto;

import com.example.accountprovider.dto.AccountDto;

import java.time.LocalDate;

public record AccountUpdateDto(String login, String password, LocalDate dateTillPassword, String email, Boolean active) {

    public AccountDto toAccountDto() {
        return new AccountDto(null, login, password, dateTillPassword, email, active);
    }

    public AccountDto toAccountDto(String id) {
        return new AccountDto(id, login, password, dateTillPassword, email, active);
    }

}
