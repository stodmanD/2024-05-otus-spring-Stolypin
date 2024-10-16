package com.example.hw15spring_boot_actuator.repositories;

import com.example.hw15spring_boot_actuator.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "genre")
public interface GenreRepository extends JpaRepository<Genre, Long> {}
