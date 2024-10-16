package com.example.hw15spring_boot_actuator.actuators;

import com.example.hw15spring_boot_actuator.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class BooksHealthIndicator implements HealthIndicator {
    private static final Health STATUS_DOWN = Health.down()
            .withDetail("message", "Book storage is empty")
            .build();

    private static final Health STATUS_UP = Health.up()
            .withDetail("message", "Everything is good, so far)")
            .build();

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        boolean storageEmpty;
        try {
            storageEmpty = bookRepository.count() == 0;
        } catch (RuntimeException e) {
            log.error("Error while accessing book repository", e);
            return STATUS_DOWN;
        }
        if (storageEmpty) {
            return STATUS_DOWN;
        } else {
            return STATUS_UP;
        }
    }
}
