package com.example.hw03springboot.service.impl;

import com.example.hw03springboot.config.LocaleConfig;
import com.example.hw03springboot.service.LocalizedMessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class LocalizedMessagesServiceImpl implements LocalizedMessagesService {

    private final LocaleConfig localeConfig;

    private final MessageSource messageSource;

    @Override
    public String getMessage(String code, Object ...args) {
        return messageSource.getMessage(code, args, localeConfig.getLocale());
    }

}
