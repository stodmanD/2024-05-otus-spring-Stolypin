package com.example.hw11spring_security_authentication.repositories;

import com.example.hw11spring_security_authentication.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Long> {}
