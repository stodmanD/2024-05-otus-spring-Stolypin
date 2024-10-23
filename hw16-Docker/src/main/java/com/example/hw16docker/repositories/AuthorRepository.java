package com.example.hw16docker.repositories;

import com.example.hw16docker.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "author")
public interface AuthorRepository extends JpaRepository<Author, Long> {}
