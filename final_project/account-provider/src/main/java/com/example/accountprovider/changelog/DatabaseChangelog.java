package com.example.accountprovider.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.example.accountprovider.model.Account;
import com.example.accountprovider.repository.AccountRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {

    private static final String AUTHOR = "stodman"; //changelog author


    @ChangeSet(order = "001", id = "dropDb", author = AUTHOR, runAlways = true)
    public void dropDb(MongoDatabase mdb) {
        mdb.drop();
    }

    @ChangeSet(order = "002", id = "insertAccounts", author = AUTHOR, runAlways = true)
    public void insertAuthors(AccountRepository accountRepository) {
        List<Account> accounts = new ArrayList<>(10);
        accounts.add(new Account("66a52bbad153b0189d3d4af0", "v.nikishina", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "v.nikishina@comand.su", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af1", "s.tarasova", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "s.tarasova@comand.su", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af2", "i.kravtsov", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "i.kravtsov@comand.su", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af3", "s.nikitin", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "s.nikitin@comand.su", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af4", "a.vavilov", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "a.vavilov@comand.su", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af5", "b.bogomolov", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "b.bogomolov@comand.su", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af6", "o.kolpakova", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "o.kolpakova@comand.su", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af9", "g.vasiliev", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "g.vasiliev@comand.su", true));
        accounts.add(new Account("66a52bbad153b0189d3d5af9", "n.melnikov", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "n.melnikov", true));
        accounts.add(new Account("66a58bbad153b0189d3d5af9", "f.afanasiev", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "f.afanasiev", true));
        accountRepository.saveAll(accounts);
    }
}
