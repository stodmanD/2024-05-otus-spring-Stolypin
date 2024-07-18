package com.example.hw03springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;
import java.util.Map;

// Использовать @ConfigurationProperties.
// Сейчас класс соответствует файлу настроек. Чтобы они сюда отобразились нужно только правильно разместить аннотации

@Data
@ConfigurationProperties(prefix = "test")
public class AppProperties implements TestConfig, TestFileNameProvider, LocaleConfig {

    private Integer rightAnswersCountToPass;

    private Map<String, String> fileNameByLocaleTag;

    private Locale locale;

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }


    @Override
    public Locale getLocale() {
        return locale;
    }
}
