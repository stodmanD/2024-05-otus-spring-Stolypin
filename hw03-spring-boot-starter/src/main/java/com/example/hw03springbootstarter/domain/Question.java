package com.example.hw03springbootstarter.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
