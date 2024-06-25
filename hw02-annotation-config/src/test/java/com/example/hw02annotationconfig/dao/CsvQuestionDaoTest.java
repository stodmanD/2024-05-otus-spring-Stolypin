package com.example.hw02annotationconfig.dao;

import com.example.hw02annotationconfig.config.TestFileNameProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Test dao class")
public class CsvQuestionDaoTest {

    private static final String CSV_QUESTIONS_FILE_NAME = "questionsTest.csv";

    private final TestFileNameProvider mockFileNameProvider = Mockito.mock(TestFileNameProvider.class);
    private final CsvQuestionDao underTestService = new CsvQuestionDao(mockFileNameProvider);

    @Test
    @DisplayName("Test count questions from csv file")
    void testCountAnswersFromCSV() {
        // given
        var expectedSizeOfAnswers = 6;

        //when
        when(mockFileNameProvider.getTestFileName()).thenReturn(CSV_QUESTIONS_FILE_NAME);

        //then
        var actualSizeOfAnswers = underTestService.findAll().size();

        assertEquals(expectedSizeOfAnswers, actualSizeOfAnswers);
    }

    @Test
    @DisplayName("Get first question and equal it in expected")
    void testFirstQuestionFromCSVFile() {
        //given
        var expectedFirstQuestion = "Will this homework be accepted from me?";

        //when
        when(mockFileNameProvider.getTestFileName()).thenReturn(CSV_QUESTIONS_FILE_NAME);

        //then
        var actualFirstQuestion = underTestService.findAll().get(0).text();

        assertEquals(expectedFirstQuestion, actualFirstQuestion);
    }
}
