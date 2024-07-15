package com.example.hw03springbootstarter.dao;



import com.example.hw03springbootstarter.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
