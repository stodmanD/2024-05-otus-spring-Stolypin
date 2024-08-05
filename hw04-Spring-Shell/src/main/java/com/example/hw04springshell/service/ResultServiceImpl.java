package com.example.hw04springshell.service;

import com.example.hw04springshell.config.TestConfig;
import com.example.hw04springshell.domain.TestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final LocalizedIOService localizedIoService;

    @Override
    public void showResult(TestResult testResult) {
        localizedIoService.printLine("");
        localizedIoService.printLineLocalized("ResultService.test.results");
        localizedIoService.printFormattedLineLocalized("ResultService.student",
                testResult.getStudent().getFullName());
        localizedIoService.printFormattedLineLocalized("ResultService.answered.questions.count",
                testResult.getAnsweredQuestions().size());
        localizedIoService.printFormattedLineLocalized(
                "ResultService.right.answers.count",
                testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            localizedIoService.printLineLocalized("ResultService.passed.test");
            return;
        }
        localizedIoService.printLineLocalized("ResultService.fail.test");
    }
}
