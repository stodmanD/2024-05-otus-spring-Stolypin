package com.example.hw03springboot.service;

import com.example.hw03springboot.config.TestConfig;
import com.example.hw03springboot.domain.TestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final LocalizedIOService ioService;

    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine("");
        ioService.printLineLocalized("ResultService.test.results");
        ioService.printFormattedLineLocalized("ResultService.student",
                testResult.getStudent().getFullName());
        ioService.printFormattedLineLocalized("ResultService.answered.questions.count",
                testResult.getAnsweredQuestions().size());
        ioService.printFormattedLineLocalized("ResultService.right.answers.count",
                testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            ioService.printLineLocalized("ResultService.passed.test");
            return;
        }
        ioService.printLineLocalized("ResultService.fail.test");
    }
}
