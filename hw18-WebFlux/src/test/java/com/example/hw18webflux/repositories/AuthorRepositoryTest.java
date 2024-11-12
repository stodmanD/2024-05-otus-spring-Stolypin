package com.example.hw18webflux.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import com.example.hw18webflux.models.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе ReactiveMongoRepository для работы с авторами ")
class AuthorRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("должен загружать автора по id")
    @Test
    void shouldReturnCorrectAuthorById() {
        List<Author> authors = mongoTemplate.findAll(Author.class).toStream().toList();
        Author expectedAuthor = authors.get(0);

        Mono<Author> authorMono = authorRepository.findById(expectedAuthor.getId());

        StepVerifier
                .create(authorMono)
                .assertNext(author -> assertThat(author)
                        .usingRecursiveComparison()
                        .isEqualTo(expectedAuthor))
                .expectComplete()
                .verify();
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        Flux<Author> authorsFlux = authorRepository.findAll();

        List<Author> authors = authorsFlux.toStream().toList();
        assertEquals(3, authorsFlux.toStream().toList().size());
        authors.forEach(System.out::println);
    }
}