package com.example.hw01.service.impl;

import com.example.hw01.service.TestRunnerService;
import com.example.hw01.service.TestService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    @Override
    public void run() {
        testService.executeTest();
    }
}
