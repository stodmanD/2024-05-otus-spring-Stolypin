package com.example.reportsender.configuration.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue birthdaysQueue(
            @Value("${service.mq.queues.birthdays}") String birthdaysQueueName
    ) {
        return new Queue(birthdaysQueueName);
    }

    @Bean
    public Queue expiringAccountsQueue(
            @Value("${service.mq.queues.expiring-accounts}") String expiringAccountsQueueName
    ) {
        return new Queue(expiringAccountsQueueName);
    }

    @Bean
    public Queue disabledAccountsQueue(
            @Value("${service.mq.queues.disabled-accounts}") String disabledAccountsQueueName
    ) {
        return new Queue(disabledAccountsQueueName);
    }

}
