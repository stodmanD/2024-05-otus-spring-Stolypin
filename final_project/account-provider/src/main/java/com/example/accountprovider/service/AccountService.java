package com.example.accountprovider.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.accountprovider.dto.AccountDto;
import com.example.accountprovider.exception.EntityAlreadyExistsException;
import com.example.accountprovider.exception.EntityNotFoundException;
import com.example.accountprovider.model.Account;
import com.example.accountprovider.repository.AccountRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountDto create(AccountDto accountDto) {
        ValidatorService.checkAccount(accountDto);
        log.info("START CREATE account");
        if (accountRepository.findByLogin(accountDto.login()).isPresent()) {
            throw new EntityAlreadyExistsException("Account with login=%s already exists".formatted(accountDto.login()));
        }
        log.info("try CREATE account");
        Account account = new Account(
                null,
                accountDto.login(),
                accountDto.password(),
                accountDto.dateTillPassword(),
                accountDto.email(),
                accountDto.active()
        );
        log.info("try to save");
        Account savedAccount = accountRepository.save(account);
        return AccountDto.fromEntity(savedAccount);
    }

    @Transactional(readOnly = true)
    public AccountDto findById(String id) {
        return accountRepository.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Account with id=%s not found".formatted(id)));
    }

    @Transactional
    public AccountDto update(AccountDto accountDto) {
        ValidatorService.checkAccount(accountDto);
        String id = accountDto.id();
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id=%s not found".formatted(id)));
        String login = accountDto.login();
        Optional<Account> byLogin = accountRepository.findByLogin(login);
        if (byLogin.isPresent() && !byLogin.get().getId().equals(account.getId())) {
            throw new EntityAlreadyExistsException("Account with login=%s already exists".formatted(login));
        }
        String email = accountDto.email();
        Optional<Account> byEmail = accountRepository.findByEmail(email);
        if (byEmail.isPresent() && !byEmail.get().getId().equals(account.getId())) {
            throw new EntityAlreadyExistsException("Account with email=%s already exists".formatted(email));
        }
        account.setLogin(login);
        account.setPassword(accountDto.password());
        account.setDateTillPassword(accountDto.dateTillPassword());
        account.setEmail(email);
        account.setActive(accountDto.active());
        Account updatedAccount = accountRepository.save(account);
        return AccountDto.fromEntity(updatedAccount);
    }

    @Transactional
    public void delete(String id) {
        accountRepository.deleteById(id);
    }

    @Transactional
    public List<AccountDto> offExpired() {
        return accountRepository.findAll()
                .stream()
                .filter(account -> account.getDateTillPassword().isBefore(LocalDate.now()))
                .peek(account -> {
                    account.setActive(false);
                    accountRepository.save(account);
                })
                .map(AccountDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<String> loginsWithExpiredPassword(LocalDate dateTillPassword) {
        ValidatorService.checkExpireDate(dateTillPassword);
        return accountRepository.findByDateTillPassword(dateTillPassword)
                .stream()
                .filter(Account::getActive)
                .map(Account::getLogin)
                .toList();
    }

    @Transactional
    public List<AccountDto> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(AccountDto::fromEntity)
                .toList();
    }

}
