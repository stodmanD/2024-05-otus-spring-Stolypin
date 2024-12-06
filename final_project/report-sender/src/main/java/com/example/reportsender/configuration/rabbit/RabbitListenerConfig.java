package com.example.reportsender.configuration.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import com.example.reportsender.api.BirthdayInfo;
import com.example.reportsender.api.DisabledAccounts;
import com.example.reportsender.api.ExpireAccounts;
import com.example.reportsender.service.MessageProcessService;

@Configuration
@RequiredArgsConstructor
public class RabbitListenerConfig {

    private final MessageProcessService messageProcessService;


    @RabbitListener(queues = {"${service.mq.queues.birthdays}"})
    public void listenBirthdays(BirthdayInfo birthdayInfo) {
        messageProcessService.processOnMessage(birthdayInfo);
    }

    @RabbitListener(queues = {"${service.mq.queues.expiring-accounts}"})
    public void listenExpiringAccounts(ExpireAccounts expireAccounts) {
        messageProcessService.processOnMessage(expireAccounts);
    }

    @RabbitListener(queues = {"${service.mq.queues.disabled-accounts}"})
    public void listenDisabledAccounts(DisabledAccounts disabledAccounts) {
        messageProcessService.processOnMessage(disabledAccounts);
    }

}
