package com.example.accountprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.accountprovider.dto.AccountUpdateDto;
import com.example.accountprovider.dto.AccountDto;
import com.example.accountprovider.service.AccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountCrudController {

    private final AccountService accountService;


    @PostMapping("/account")
    public AccountDto create(@RequestBody AccountUpdateDto accountUpdateDto) {

        return accountService.create(accountUpdateDto.toAccountDto());
    }

    @GetMapping("/account/{id}")
    public AccountDto read(@PathVariable String id) {
        return accountService.findById(id);
    }

    @GetMapping("/account/all")
    public List<AccountDto> allAccount() {
        return accountService.findAll();
    }

    @PutMapping("/account/{id}")
    public AccountDto update(@PathVariable String id, @RequestBody AccountUpdateDto accountUpdateDto) {
        return accountService.update(accountUpdateDto.toAccountDto(id));
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable String id) {
        accountService.delete(id);
    }

}

