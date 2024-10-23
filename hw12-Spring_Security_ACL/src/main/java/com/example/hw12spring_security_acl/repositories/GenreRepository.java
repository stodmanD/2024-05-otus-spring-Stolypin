package com.example.hw12spring_security_acl.repositories;

import com.example.hw12spring_security_acl.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Long> {}
