package com.example.accountprovider.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.accountprovider.api.DisabledAccounts;
import com.example.accountprovider.api.ExpireAccounts;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RabbitProducerService {

    @Value("${service.report-sender.queues.expiring-accounts}")
    private String reportSenderExpiringAccountsQueueName;

    @Value("${service.report-sender.queues.disabled-accounts}")
    private String reportSenderDisabledAccountsQueueName;

    private final RabbitTemplate rabbitTemplate;


    public void sendExpiringAccountReport(int expireAfterDays, List<String> logins) {
        ExpireAccounts expireAccounts = new ExpireAccounts(expireAfterDays, logins);
        rabbitTemplate.convertAndSend(reportSenderExpiringAccountsQueueName, expireAccounts);
    }

    public void sendDisabledAccountReport(List<String> logins) {
        DisabledAccounts disabledAccounts = new DisabledAccounts(logins);
        rabbitTemplate.convertAndSend(reportSenderDisabledAccountsQueueName, disabledAccounts);
    }

}
