package com.example.accountprovider.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.accountprovider.dto.AccountDto;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PasswordScheduler {

    private final AccountService accountService;

    private final RabbitProducerService producerService;


    @Scheduled(cron = "0 0 8 * * *") // At 08:00 AM
    public void dateTillPassword() {
        List<String> loginsWithExpiredPassword = accountService.loginsWithExpiredPassword(LocalDate.now());
        if (!loginsWithExpiredPassword.isEmpty()) {
            producerService.sendExpiringAccountReport(1, loginsWithExpiredPassword);
        }

        List<String> loginsWith3daysExpiredPassword = accountService
                .loginsWithExpiredPassword(LocalDate.now().plusDays(3));
        if (!loginsWith3daysExpiredPassword.isEmpty()) {
            producerService.sendExpiringAccountReport(3, loginsWith3daysExpiredPassword);
        }
    }

    @Scheduled(cron = "0 1 0 * * *") // At 12:01 AM
    public void disableAccounts() {
        List<String> expiredLoginAccounts = accountService.offExpired()
                .stream()
                .map(AccountDto::login)
                .toList();
        if (!expiredLoginAccounts.isEmpty()) {
            producerService.sendDisabledAccountReport(expiredLoginAccounts);
        }
    }

}
