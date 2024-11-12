package com.example.hw18webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import com.example.hw18webflux.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findAllByOrderByTitleAsc();
}
