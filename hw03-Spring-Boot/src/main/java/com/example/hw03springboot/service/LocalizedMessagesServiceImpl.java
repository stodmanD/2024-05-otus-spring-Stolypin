package com.example.hw03springboot.service;

import com.example.hw03springboot.config.LocaleConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.messages")
public class LocalizedMessagesServiceImpl implements LocalizedMessagesService {

    private String encoding;

    private String baseName;

    private LocaleConfig localeConfig;

    @Override
    public String getMessage(String code, Object ...args) {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        messageSource.setDefaultEncoding(encoding);
        return messageSource.getMessage(code, args, localeConfig.getLocale());
    }

    @Override
    public String get(String key) {
        return getMessage(key, new Object[]{});
    }
}
