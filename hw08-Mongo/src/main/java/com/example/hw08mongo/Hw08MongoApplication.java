package com.example.hw08mongo;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class Hw08MongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hw08MongoApplication.class, args);
    }

}
