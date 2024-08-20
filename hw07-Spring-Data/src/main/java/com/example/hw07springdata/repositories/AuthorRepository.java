package com.example.hw07springdata.repositories;

import com.example.hw07springdata.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Long> {}
