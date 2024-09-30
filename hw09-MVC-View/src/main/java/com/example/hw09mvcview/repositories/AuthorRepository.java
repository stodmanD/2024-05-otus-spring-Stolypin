package com.example.hw09mvcview.repositories;

import com.example.hw09mvcview.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
