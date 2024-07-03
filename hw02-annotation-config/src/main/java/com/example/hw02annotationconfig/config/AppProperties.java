package com.example.hw02annotationconfig.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppProperties implements TestConfig, TestFileNameProvider {

    @Value("${test.rightAnswersCountToPass:3}")
    private int rightAnswersCountToPass;

    @Value("${test.fileName:questions.csv}")
    private String testFileName;

    @Value("${test.showErrors:false}")
    private boolean showErrors;

    @Override // внедрить свойство из application.properties
    public String getTestFileName() {
        return testFileName;
    }
}
