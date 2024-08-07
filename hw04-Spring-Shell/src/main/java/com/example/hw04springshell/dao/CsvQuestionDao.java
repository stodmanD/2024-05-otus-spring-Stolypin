package com.example.hw04springshell.dao;

import com.example.hw04springshell.config.TestFileNameProvider;
import com.example.hw04springshell.dao.dto.QuestionDto;
import com.example.hw04springshell.domain.Question;
import com.example.hw04springshell.exceptions.QuestionReadException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private static final String FILE_NOT_FOUND_ERROR_MESSAGE = "File not found: ";

    private static final String IO_ERROR_MESSAGE = "Error while closing input stream. Filename: ";

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        String testFileName = fileNameProvider.getTestFileName();

        InputStream is = getClass().getClassLoader().getResourceAsStream(testFileName);
        if (is == null) {
            throw new QuestionReadException(FILE_NOT_FOUND_ERROR_MESSAGE + testFileName, null);
        }
        CsvToBean<QuestionDto> csvToBean;
        try (InputStreamReader reader = new InputStreamReader(is)) {
            csvToBean = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .withSeparator(';')
                    .build();
            List<QuestionDto> dtoList = csvToBean.parse();
            return dtoList.stream()
                    .map(QuestionDto::toDomainObject)
                    .toList();
        } catch (IOException e) {
            throw new QuestionReadException(IO_ERROR_MESSAGE + testFileName, e);
        }
    }
}
