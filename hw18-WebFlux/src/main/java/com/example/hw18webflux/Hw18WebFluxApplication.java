package com.example.hw18webflux;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class Hw18WebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hw18WebFluxApplication.class, args);
    }

}
