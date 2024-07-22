package com.example.hw03springboot.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
