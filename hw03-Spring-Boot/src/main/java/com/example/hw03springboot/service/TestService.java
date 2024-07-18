package com.example.hw03springboot.service;


import com.example.hw03springboot.domain.Student;
import com.example.hw03springboot.domain.TestResult;

public interface TestService {
    TestResult executeTestFor(Student student);

    void chooseLanguage();
}
