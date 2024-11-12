package com.example.hw18webflux.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.example.hw18webflux.models.Comment;


public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findByBookId(String bookId);

    Mono<Void> deleteByBookId(String bookId);
}
