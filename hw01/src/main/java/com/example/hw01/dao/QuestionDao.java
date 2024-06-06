package com.example.hw01.dao;



import com.example.hw01.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
