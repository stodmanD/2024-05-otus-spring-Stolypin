package com.example.hw10springmvc.repositories;

import com.example.hw10springmvc.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Long> {}
