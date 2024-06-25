package com.example.hw02annotationconfig.service;


import com.example.hw02annotationconfig.domain.Student;
import com.example.hw02annotationconfig.domain.TestResult;

public interface TestService {
    TestResult executeTestFor(Student student);
}
