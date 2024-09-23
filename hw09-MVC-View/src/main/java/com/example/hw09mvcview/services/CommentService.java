package com.example.hw09mvcview.services;


import com.example.hw09mvcview.dto.response.CommentDto;

public interface CommentService {
    CommentDto create(long bookId, String commentText);

    CommentDto findById(long id);
}
