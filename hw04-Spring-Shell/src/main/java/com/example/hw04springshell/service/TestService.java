package com.example.hw04springshell.service;


import com.example.hw04springshell.domain.Student;
import com.example.hw04springshell.domain.TestResult;

public interface TestService {
    TestResult executeTestFor(Student student);
}
