package com.example.employeemanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.employeemanagement.dto.PersonalInfoDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BirthdayScheduler {

    private final PersonalInfoService personalInfoService;

    private final RabbitProducerService producerService;


    @Scheduled(cron = "0 0 8 * * *") // At 08:00 AM
    public void birthdayToday() {
        List<String> birthdayBoys = personalInfoService.birthdayToday()
                .stream()
                .map(PersonalInfoDto::fullName)
                .toList();

        if (!birthdayBoys.isEmpty()) {
            producerService.sendBirthdaysReport(true, birthdayBoys);
        }
    }

    @Scheduled(cron = "0 0 8 28 * *") // At 08:00 AM, on day 28 of the month
    public void birthdayNextMonth() {
        List<String> birthdayBoys = personalInfoService.birthdayNextMonth()
                .stream()
                .map(PersonalInfoDto::fullName)
                .toList();

        if (!birthdayBoys.isEmpty()) {
            producerService.sendBirthdaysReport(false, birthdayBoys);
        }
    }

}
