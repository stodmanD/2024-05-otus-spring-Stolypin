package com.example.hw14spring_integration.services.educationservices;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.hw14spring_integration.models.Beginner;
import com.example.hw14spring_integration.models.EasyRunner;

@Slf4j
@Service
public class AnyFreeCourse {
    @SneakyThrows
    public EasyRunner teach(Beginner beginner) {
        while (beginner.getSkillLevel() < 7) {
            Thread.sleep(100);
            log.info("{} просмотрел еще один бесплатный курс на Youtube", beginner.getName());
            beginner.skillLevelUp();
        }
        return new EasyRunner(beginner);
    }

}
