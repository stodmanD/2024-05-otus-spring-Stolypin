package com.example.employeemanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.employeemanagement.api.BirthdayInfo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitProducerService {

    @Value("${service.report-sender.queues.birthdays}")
    private String reportSenderQueueName;

    private final RabbitTemplate rabbitTemplate;


    public void sendBirthdaysReport(boolean isToday, List<String> birthdayBoys) {
        BirthdayInfo birthdayInfo = new BirthdayInfo(isToday, birthdayBoys);
        rabbitTemplate.convertAndSend(reportSenderQueueName, birthdayInfo);
    }

}
