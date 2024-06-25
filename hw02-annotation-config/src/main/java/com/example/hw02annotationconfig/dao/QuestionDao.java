package com.example.hw02annotationconfig.dao;


import com.example.hw02annotationconfig.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
