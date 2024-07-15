package com.example.hw03springbootstarter.service;


import com.example.hw03springbootstarter.domain.Student;
import com.example.hw03springbootstarter.domain.TestResult;

public interface TestService {
    TestResult executeTestFor(Student student);
}
