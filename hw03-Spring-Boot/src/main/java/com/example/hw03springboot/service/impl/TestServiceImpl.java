package com.example.hw03springboot.service.impl;

import com.example.hw03springboot.config.AppProperties;
import com.example.hw03springboot.dao.QuestionDao;
import com.example.hw03springboot.domain.Answer;
import com.example.hw03springboot.domain.Student;
import com.example.hw03springboot.domain.TestResult;
import com.example.hw03springboot.service.LocalizedIOService;
import com.example.hw03springboot.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    private final AppProperties appProperties;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLineLocalized(("answerQuestion"));
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        for (var question : questions) {
            ioService.printFormattedLine("%n %s", question.text());
            int numberAnswer = 1;
            for (Answer answer : question.answers()) {
                ioService.printFormattedLine("%d: %s", numberAnswer, answer.text());
                numberAnswer++;
            }
            var studentAnswer = ioService.readIntForRange(1, question.answers().size(),
                    "incorrectInput");
            var isAnswerValid = question.answers().get(studentAnswer - 1).isCorrect();
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

}
