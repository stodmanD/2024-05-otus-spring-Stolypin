package com.example.hw14spring_integration.services.educationservices;

import com.example.hw14spring_integration.models.DeveloperPro;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.hw14spring_integration.models.Developer;

@Slf4j
@Service
public class OtusProfessionalCourse {
    @SneakyThrows
    public DeveloperPro teach(Developer developer) {
        int level = 1;
        while (level < 3) {
            Thread.sleep(100);
            developer.skillLevelUp();
            log.info("{} закончил {} профессиональный курс Otus", developer.getName(), level);
            level++;
        }
        log.info("Поздравляем, теперь {} профессиональный разработчик!", developer.getName());
        return new DeveloperPro(developer);
    }
}
