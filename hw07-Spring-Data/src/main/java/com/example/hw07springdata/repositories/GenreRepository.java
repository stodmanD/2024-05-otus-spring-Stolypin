package com.example.hw07springdata.repositories;

import com.example.hw07springdata.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Long> {}
