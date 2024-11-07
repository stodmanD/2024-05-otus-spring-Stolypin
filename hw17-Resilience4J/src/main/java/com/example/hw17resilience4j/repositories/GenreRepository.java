package com.example.hw17resilience4j.repositories;

import com.example.hw17resilience4j.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Long> {}
