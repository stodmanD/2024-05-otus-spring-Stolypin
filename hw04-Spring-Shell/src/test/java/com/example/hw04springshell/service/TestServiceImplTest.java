package com.example.hw04springshell.service;

import com.example.hw04springshell.dao.QuestionDao;
import com.example.hw04springshell.domain.Answer;
import com.example.hw04springshell.domain.Question;
import com.example.hw04springshell.domain.Student;
import com.example.hw04springshell.exceptions.QuestionReadException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Testing service class")
@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class TestServiceImplTest {
    private static final String STUDENT_FIRST_NAME = "Bob";
    private static final String STUDENT_LAST_NAME = "Donovan";

    @MockBean
    private LocalizedIOService localizedIoService;
    @MockBean
    private QuestionDao questionDao;
    @Autowired
    private TestServiceImpl testServiceImpl;

    @AfterEach
    void after() {
        verifyNoMoreInteractions(questionDao, localizedIoService);
    }

    @Test
    @DisplayName("Check TestServiceImpl. Empty list from DAO")
    void testCheckEmptyList() {
        List<Question> daoResult = Collections.emptyList();
        when(questionDao.findAll()).thenReturn(daoResult);
        Student student = new Student(STUDENT_FIRST_NAME, STUDENT_LAST_NAME);

        testServiceImpl.executeTestFor(student);

        assertAll("Check TestServiceImpl on empty list",
                () -> verify(localizedIoService, times(1)).printLineLocalized(anyString()),
                () -> verify(questionDao, times(1)).findAll()
        );
    }

    @Test
    @DisplayName("Check TestServiceImpl. One record from DAO")
    void testCheckOneRecord() {
        List<Answer> answers =
                List.of(new Answer("Answer one", false), new Answer("Answer two", true));
        Question question = new Question("Question one", answers);
        List<Question> daoResult = Collections.singletonList(question);
        when(questionDao.findAll()).thenReturn(daoResult);
        when(localizedIoService.readIntForRangeWithPromptLocalized(eq(1), eq(answers.size()), anyString(), anyString()))
                .thenReturn(1);
        Student student = new Student(STUDENT_FIRST_NAME, STUDENT_LAST_NAME);

        testServiceImpl.executeTestFor(student);

        assertAll("Check TestServiceImpl, one record from DAO",
                () -> verify(questionDao, times(1)).findAll(),
                () -> verify(localizedIoService, times(1)).printLineLocalized(anyString()),
                () -> verify(localizedIoService, times(1)).printFormattedLine(anyString(), any()),
                () -> verify(localizedIoService, times(2)).printFormattedLine(anyString(), any(), any()),
                () -> verify(localizedIoService, times(1))
                        .readIntForRangeWithPromptLocalized(eq(1), eq(answers.size()), anyString(), anyString())
        );
    }

    @Test
    @DisplayName("Check TestServiceImpl. Exception from DAO")
    void testCheckException() {
        doThrow(QuestionReadException.class).when(questionDao).findAll();
        Student student = new Student(STUDENT_FIRST_NAME, STUDENT_LAST_NAME);

        assertThrows(QuestionReadException.class, () -> testServiceImpl.executeTestFor(student));

        assertAll("Check TestServiceImpl, exception from DAO",
                () -> verify(questionDao, times(1)).findAll(),
                () -> verify(localizedIoService, times(1)).printLineLocalized(anyString())
        );
    }

}