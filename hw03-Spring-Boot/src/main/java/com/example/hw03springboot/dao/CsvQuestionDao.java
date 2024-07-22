package com.example.hw03springboot.dao;

import com.example.hw03springboot.config.TestFileNameProvider;
import com.example.hw03springboot.dao.dto.QuestionDto;
import com.example.hw03springboot.domain.Question;
import com.example.hw03springboot.exceptions.QuestionReadException;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider testFileNameProvider;

    @Override
    public List<Question> findAll() {
        var dsd = testFileNameProvider.getTestFileName();

        try (var inputStream = getClass().getClassLoader()
                .getResourceAsStream(testFileNameProvider.getTestFileName())) {
            if (inputStream != null) {
                return processCsvFile(inputStream);
            }
            throw new QuestionReadException("error.read.file");
        } catch (Exception exception) {
            throw new QuestionReadException(exception.getMessage(), exception);
        }
    }

    private List<Question> processCsvFile(InputStream inputStream) {
        var reader = new InputStreamReader(inputStream);
        var csvReader = new CsvToBeanBuilder<QuestionDto>(reader)
                .withType(QuestionDto.class)
                .withSeparator(';')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .withSkipLines(1)
                .build();
        return csvReader.parse().stream()
                .map(QuestionDto::toDomainObject)
                .collect(Collectors.toList());
    }

}
