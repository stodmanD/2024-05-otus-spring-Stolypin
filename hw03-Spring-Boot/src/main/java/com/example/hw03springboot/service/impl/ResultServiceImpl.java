package com.example.hw03springboot.service.impl;

import com.example.hw03springboot.config.AppProperties;
import com.example.hw03springboot.domain.TestResult;
import com.example.hw03springboot.service.LocalizedIOService;
import com.example.hw03springboot.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final AppProperties appProperties;

    private final LocalizedIOService ioService;

    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine("");
        ioService.printFormattedLineLocalized("showResult.testResult");
        ioService.printFormattedLineLocalized("showResult.student",
                testResult.getStudent().getFullName());
        ioService.printFormattedLineLocalized("showResult.answered.questions",
                testResult.getAnsweredQuestions().size());
        ioService.printFormattedLineLocalized("showResult.right.answers.count",
                testResult.getRightAnswersCount());
        if (testResult.getRightAnswersCount() >= appProperties.getRightAnswersCountToPass()) {
            ioService.printFormattedLineLocalized("showResult.succeed");
            return;
        }
        ioService.printLine("showResult.fail");
    }

}
