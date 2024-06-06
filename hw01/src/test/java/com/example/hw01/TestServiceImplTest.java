package com.example.hw01;

import com.example.hw01.dao.QuestionDao;
import com.example.hw01.dao.dto.QuestionDto;
import com.example.hw01.domain.Answer;
import com.example.hw01.domain.Question;
import com.example.hw01.service.IOService;
import com.example.hw01.service.impl.TestServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@DisplayName("Класс TestServiceImpl")
public class TestServiceImplTest {

    private final IOService mockIoService = Mockito.mock(IOService.class);
    private final QuestionDao mockDao = Mockito.mock(QuestionDao.class);

    public final TestServiceImpl underTestService = new TestServiceImpl(mockIoService, mockDao);

    @Test
    @DisplayName("Попытка написать тест")
    void shouldCorrectPrintInformation() {
        //given

        //then
        when(mockDao.findAll()).thenReturn(getMockListQuestions());
        underTestService.executeTest();

        //when
    }

    private List<Question> getMockListQuestions() {
        var resultList = new ArrayList<Question>();
        resultList.add(getMockQuestion());
        return resultList;
    }

    private Question getMockQuestion() {
        var questionDto = new QuestionDto();
        questionDto.setText("Is test correct?");
        var answerFirst = new Answer("Normally", true);
        var answerSecond = new Answer("No. You need to study the testing material", false);
        var answers = new ArrayList<Answer>();
        answers.add(answerFirst);
        answers.add(answerSecond);
        questionDto.setAnswers(answers);
        return questionDto.toDomainObject();
    }

}
