package com.example.hw03springboot.service;

public interface LocalizedMessagesService {

    String getMessage(String code, Object ...args);

    String get(String key);
}
