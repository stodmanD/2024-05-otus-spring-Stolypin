package com.example.hw01.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
