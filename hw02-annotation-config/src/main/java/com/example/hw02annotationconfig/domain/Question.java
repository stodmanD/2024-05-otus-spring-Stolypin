package com.example.hw02annotationconfig.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
