package com.example.hw17resilience4j.repositories;

import com.example.hw17resilience4j.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Long> {}
