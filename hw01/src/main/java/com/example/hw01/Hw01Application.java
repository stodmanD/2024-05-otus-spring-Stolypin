package com.example.hw01;

import com.example.hw01.service.TestRunnerService;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Hw01Application {
    private static final String SPRING_CONTEXT_FILE_NAME = "spring-context.xml";

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(SPRING_CONTEXT_FILE_NAME);
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }

}
