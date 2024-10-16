package com.example.hw15spring_boot_actuator.repositories;

import com.example.hw15spring_boot_actuator.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "author")
public interface AuthorRepository extends JpaRepository<Author, Long> {}
