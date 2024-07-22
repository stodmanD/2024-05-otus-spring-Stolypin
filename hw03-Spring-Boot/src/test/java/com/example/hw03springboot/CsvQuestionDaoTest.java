package com.example.hw03springboot;

import com.example.hw03springboot.config.TestFileNameProvider;
import com.example.hw03springboot.dao.CsvQuestionDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Test dao class")
class CsvQuestionDaoTest {

    private static final String CSV_QUESTIONS_FILE_NAME_EN = "questions.csv";
    private static final String CSV_QUESTIONS_FILE_NAME_RU = "questions_ru.csv";

    private final TestFileNameProvider mockFileNameProvider = Mockito.mock(TestFileNameProvider.class);
    private final CsvQuestionDao underTestService = new CsvQuestionDao(mockFileNameProvider);

    @Test
    @DisplayName("Test count questions from csv file")
    void testCountAnswersFromCSV() {
        // given
        var expectedSizeOfAnswers = 6;

        //when
        when(mockFileNameProvider.getTestFileName()).thenReturn(CSV_QUESTIONS_FILE_NAME_EN);

        //then
        var actualSizeOfAnswers = underTestService.findAll().size();

        assertEquals(expectedSizeOfAnswers, actualSizeOfAnswers);
    }

    @Test
    @DisplayName("Get first question and equal it in expected (EN)")
    void testFirstQuestionFromCSVFileEN() {
        //given
        var expectedFirstQuestion = "Will this homework be accepted from me?";

        //when
        when(mockFileNameProvider.getTestFileName()).thenReturn(CSV_QUESTIONS_FILE_NAME_EN);

        //then
        var actualFirstQuestion = underTestService.findAll().get(0).text();

        assertEquals(expectedFirstQuestion, actualFirstQuestion);
    }

    @Test
    @DisplayName("Get first question and equal it in expected (RU)")
    void testFirstQuestionFromCSVFileRU() {
        //given
        var expectedFirstQuestion = "Будет ли принято от меня это домашнее задание?";

        //when
        when(mockFileNameProvider.getTestFileName()).thenReturn(CSV_QUESTIONS_FILE_NAME_RU);

        //then
        var actualFirstQuestion = underTestService.findAll().get(0).text();

        assertEquals(expectedFirstQuestion, actualFirstQuestion);
    }

}