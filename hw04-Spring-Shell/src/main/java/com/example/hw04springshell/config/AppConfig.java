package com.example.hw04springshell.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties (AppProperties.class)
public class AppConfig {
}
