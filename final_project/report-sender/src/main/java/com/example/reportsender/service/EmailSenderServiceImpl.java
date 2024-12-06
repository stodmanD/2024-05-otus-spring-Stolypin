package com.example.reportsender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile("mail")
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    @Value("${service.mail.from}")
    private String from;

    @Value("${service.mail.to}")
    private String to;

    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
