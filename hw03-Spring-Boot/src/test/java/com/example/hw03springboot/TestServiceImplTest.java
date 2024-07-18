package com.example.hw03springboot;

import com.example.hw03springboot.config.AppProperties;
import com.example.hw03springboot.dao.CsvQuestionDao;
import com.example.hw03springboot.domain.Answer;
import com.example.hw03springboot.domain.Question;
import com.example.hw03springboot.domain.Student;
import com.example.hw03springboot.service.LocalizedIOService;
import com.example.hw03springboot.service.TestServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testing service class")
class TestServiceImplTest {

    LocalizedIOService mockIoService = Mockito.mock(LocalizedIOService.class);
    CsvQuestionDao mockCsvQuestionDao = Mockito.mock(CsvQuestionDao.class);
    AppProperties mockAppProperties = Mockito.mock(AppProperties.class);
    TestServiceImpl underTestService = new TestServiceImpl(mockIoService, mockCsvQuestionDao, mockAppProperties);

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
        return new Student("Ben", "Buba");
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