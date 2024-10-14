package com.example.hw12spring_security_acl.repositories;

import com.example.hw12spring_security_acl.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Long> {}
