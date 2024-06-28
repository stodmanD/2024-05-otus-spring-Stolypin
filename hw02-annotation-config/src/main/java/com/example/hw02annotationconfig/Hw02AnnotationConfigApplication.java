package com.example.hw02annotationconfig;

import com.example.hw02annotationconfig.service.TestRunnerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@ComponentScan
public class Hw02AnnotationConfigApplication {

    public static void main(String[] args) {

    //Создать контекст на основе Annotation/Java конфигурирования
    ApplicationContext context = new AnnotationConfigApplicationContext(Hw02AnnotationConfigApplication.class);
    var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
}
}
