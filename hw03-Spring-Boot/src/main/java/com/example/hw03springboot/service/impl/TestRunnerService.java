package com.example.hw03springboot.service.impl;

import com.example.hw03springboot.service.ResultService;
import com.example.hw03springboot.service.StudentService;
import com.example.hw03springboot.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TestRunnerService implements ApplicationRunner {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var student = studentService.determineCurrentStudent();
        var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}
