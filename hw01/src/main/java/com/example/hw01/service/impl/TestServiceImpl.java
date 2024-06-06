package com.example.hw01.service.impl;

import com.example.hw01.dao.QuestionDao;
import com.example.hw01.domain.Answer;
import com.example.hw01.service.IOService;
import com.example.hw01.service.TestService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        var allQuestions = questionDao.findAll();

        allQuestions.forEach(element -> {
            ioService.printLine("\n" + element.text());
            int numberAnswer = 1;
            for (Answer answer : element.answers()) {
                ioService.printFormattedLine("%d, %s - %s", numberAnswer, answer.text(), answer.isCorrect());
                numberAnswer++;
            }
        });
    }
}
