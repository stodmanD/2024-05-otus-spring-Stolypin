package com.example.accountprovider.dto;

import com.example.accountprovider.model.Account;

import java.time.LocalDate;

/**
 * DTO for {@link Account}
 */
public record AccountDto(String id,
                         String login,
                         String password,
                         LocalDate dateTillPassword,
                         String email,
                         Boolean active) {

    public static AccountDto fromEntity(Account account) {
        return new AccountDto(
                account.getId(),
                account.getLogin(),
                account.getPassword(),
                account.getDateTillPassword(),
                account.getEmail(),
                account.getActive()
        );
    }

}
