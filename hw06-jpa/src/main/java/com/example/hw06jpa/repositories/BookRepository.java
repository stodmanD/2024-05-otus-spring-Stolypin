package com.example.hw06jpa.repositories;


import com.example.hw06jpa.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(long id);

    List<Book> findAll();

    Book save(Book book);

    void deleteById(long id);

}
