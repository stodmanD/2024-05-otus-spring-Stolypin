package com.example.hw01;

import com.example.hw01.config.FileNameProvider;
import com.example.hw01.dao.CsvQuestionDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Класс CsvQuestionDao")
public class CsvQuestionDaoTest {
    private static final String CSV_QUESTIONS_FILE_NAME = "questionsTest.csv";

    private final FileNameProvider mockFileNameProvider = Mockito.mock(FileNameProvider.class);
    private final CsvQuestionDao underTestService = new CsvQuestionDao(mockFileNameProvider);

    @Test
    @DisplayName("Test for answers count from CSV file")
    void testCountAnswersFromCSV() {
        // given
        var expectedSizeOfAnswers = 6;

        //when
        when(mockFileNameProvider.getFileName()).thenReturn(CSV_QUESTIONS_FILE_NAME);

        //then
        var actualSizeOfAnswers = underTestService.findAll().size();

        assertEquals(expectedSizeOfAnswers, actualSizeOfAnswers);
    }

    @Test
    @DisplayName("Get first question from csv file and equal it for expected")
    void testFirstQuestionFromCSVFile() {
        //given
        var expectedFirstQuestion = "Will this homework be accepted from me?";

        //when
        when(mockFileNameProvider.getFileName()).thenReturn(CSV_QUESTIONS_FILE_NAME);

        //then
        var actualFirstQuestion = underTestService.findAll().get(0).text();

        assertEquals(expectedFirstQuestion, actualFirstQuestion);
    }
}
