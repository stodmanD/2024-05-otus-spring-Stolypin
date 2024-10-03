package com.example.hw08mongo.repository;


import com.example.hw08mongo.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findAll();
}
