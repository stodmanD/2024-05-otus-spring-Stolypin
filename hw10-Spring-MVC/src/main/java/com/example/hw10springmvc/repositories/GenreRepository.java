package com.example.hw10springmvc.repositories;

import com.example.hw10springmvc.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Long> {}
