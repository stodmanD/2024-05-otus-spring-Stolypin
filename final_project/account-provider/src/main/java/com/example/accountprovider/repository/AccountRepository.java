package com.example.accountprovider.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.accountprovider.model.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByLogin(String login);

    Optional<Account> findByEmail(String email);

    List<Account> findByDateTillPassword(LocalDate dateTillPassword);

}
