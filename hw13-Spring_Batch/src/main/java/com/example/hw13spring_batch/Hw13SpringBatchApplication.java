package com.example.hw13spring_batch;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class Hw13SpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hw13SpringBatchApplication.class, args);
    }

}
