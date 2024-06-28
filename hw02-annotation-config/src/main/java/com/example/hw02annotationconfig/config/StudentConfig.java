package com.example.hw02annotationconfig.config;

import com.example.hw02annotationconfig.service.IOService;
import com.example.hw02annotationconfig.service.StudentService;
import com.example.hw02annotationconfig.service.StudentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Repository
public class StudentConfig {
    @Bean
    public StudentService getStudentService(IOService ioService) {
        return new StudentServiceImpl(ioService);
    }
}
