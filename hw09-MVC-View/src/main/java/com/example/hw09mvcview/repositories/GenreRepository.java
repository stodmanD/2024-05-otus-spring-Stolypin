package com.example.hw09mvcview.repositories;

import com.example.hw09mvcview.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {}
