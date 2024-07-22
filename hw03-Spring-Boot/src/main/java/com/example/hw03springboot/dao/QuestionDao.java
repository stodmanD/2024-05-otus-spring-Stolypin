package com.example.hw03springboot.dao;


import com.example.hw03springboot.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
