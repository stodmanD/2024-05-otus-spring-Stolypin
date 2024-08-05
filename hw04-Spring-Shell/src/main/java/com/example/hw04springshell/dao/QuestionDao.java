package com.example.hw04springshell.dao;

import com.example.hw04springshell.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
