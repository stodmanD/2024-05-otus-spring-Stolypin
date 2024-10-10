package com.example.hw11spring_security_authentication.repositories;

import com.example.hw11spring_security_authentication.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Long> {}
