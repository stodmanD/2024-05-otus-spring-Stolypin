package com.example.hw12spring_security_acl.services;


import com.example.hw12spring_security_acl.dto.response.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(long genreId);
}
