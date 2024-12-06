package com.example.reportsender.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!mail")
public class EmailSenderServiceStub implements EmailSenderService {

    @Override
    public void sendEmail(String subject, String text) {
        System.out.println("==========================");
        System.out.println("Subject: " + subject + System.lineSeparator() + "Text: " + System.lineSeparator() + text);
        System.out.println("==========================");
    }
}
