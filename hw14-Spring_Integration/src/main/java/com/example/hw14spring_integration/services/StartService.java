package com.example.hw14spring_integration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import com.example.hw14spring_integration.models.Beginner;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StartService implements CommandLineRunner {

    private final HumanEducationGateway gateway;

    @Override
    public void run(String... args) {
        List<Beginner> beginners = new LinkedList<>();
//        for (int i = 0; i < 5; ++i) {
//            beginners.add(new Beginner("Name%d".formatted(i)));
//        }
        beginners.add(Beginner.createCoding("Иван"));
        beginners.add(Beginner.createCoding("Марья"));
        gateway.humanEducationProcess(beginners);
    }
}
