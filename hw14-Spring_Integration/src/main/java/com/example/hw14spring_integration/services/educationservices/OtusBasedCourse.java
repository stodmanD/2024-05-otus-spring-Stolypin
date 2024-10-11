package com.example.hw14spring_integration.services.educationservices;

import com.example.hw14spring_integration.models.Developer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.hw14spring_integration.models.EasyRunner;

@Slf4j
@Service
public class OtusBasedCourse {
    @SneakyThrows
    public Developer teach(EasyRunner easyRunner) {
        int level = 1;
        while (level < 3) {
            Thread.sleep(100);
            log.info("{} закончил {} базовый курс Otus", easyRunner.getName(), level);
            easyRunner.skillLevelUp();
            level++;
        }
        log.info("Поздравляем {}, теперь вы начинающий разработчик!", easyRunner.getName());
        return new Developer(easyRunner);
    }
}
