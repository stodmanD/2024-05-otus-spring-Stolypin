package com.example.hw03springbootstarter.service;

import com.example.hw03springbootstarter.config.LocaleConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;


@RequiredArgsConstructor
@Service
public class LocalizedMessagesServiceImpl implements LocalizedMessagesService {

    private final MessageSource messageSource;

    private final LocaleConfig localeConfig;

    @Value("${locale}")
    private Locale locale;


    // Доделать
    @Override
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, locale);
//        return messageSource.getMessage(code, args, new Locale(localeConfig.getLocale()));
    }

    @Override
    public String get(String key) {
        return getMessage(key, new Object[]{});
    }
}
