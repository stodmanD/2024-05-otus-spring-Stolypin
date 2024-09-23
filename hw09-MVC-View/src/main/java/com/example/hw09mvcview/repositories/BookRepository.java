package com.example.hw09mvcview.repositories;

import com.example.hw09mvcview.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(attributePaths = "author")
    List<Book> findAllByOrderByTitleAsc();

    @EntityGraph(attributePaths = {"author", "genres"})
    Optional<Book> findById(long id);
}
