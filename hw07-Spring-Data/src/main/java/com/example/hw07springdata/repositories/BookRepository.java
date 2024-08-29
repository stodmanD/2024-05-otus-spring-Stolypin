package com.example.hw07springdata.repositories;

import com.example.hw07springdata.models.Book;
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
