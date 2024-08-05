package com.example.hw04springshell.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
