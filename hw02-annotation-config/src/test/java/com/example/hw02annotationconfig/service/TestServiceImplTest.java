package com.example.hw02annotationconfig.service;

import com.example.hw02annotationconfig.dao.CsvQuestionDao;
import com.example.hw02annotationconfig.domain.Answer;
import com.example.hw02annotationconfig.domain.Question;
import com.example.hw02annotationconfig.domain.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Testing service class")
public class TestServiceImplTest {
    IOService mockIoService = Mockito.mock(IOService.class);
    CsvQuestionDao mockCsvQuestionDao = Mockito.mock(CsvQuestionDao.class);
    TestServiceImpl underTestService = new TestServiceImpl(mockIoService, mockCsvQuestionDao);

    @Test
    @DisplayName("All correct answers")
    void testAllCorrectAnswers() {
        //give
        var expectedCorrectAnswers = 2;

        //when
        when(mockCsvQuestionDao.findAll()).thenReturn(getMockQuestions());
        when(mockIoService.readIntForRange(anyInt(), anyInt(), anyString())).thenReturn(1);

        //then
        var testResult = underTestService.executeTestFor(getMockStudent());
        var actualCorrectAnswers = testResult.getRightAnswersCount();

        assertEquals(expectedCorrectAnswers, actualCorrectAnswers);
    }

    private Student getMockStudent() {
        return new Student("Dmitry", "Stolypin");
    }

    private List<Question> getMockQuestions() {
        var questionFirst = new Question("Will this homework be accepted from me?",
                Arrays.asList(new Answer("Yes", true),
                        new Answer("No", false)));
        var questionSecond = new Question("Who killed Bob?",
                Arrays.asList(new Answer("Y do not know", true)
                        ,new Answer("Bubamara", false),
                        new Answer("Kusturica", false)));
        return Arrays.asList(questionFirst, questionSecond);
    }
}
