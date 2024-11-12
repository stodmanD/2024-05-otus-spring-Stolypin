package com.example.hw18webflux.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.core.publisher.Flux;
import com.example.hw18webflux.models.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе ReactiveMongoRepository для работы с жанрами ")
class GenreRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("должен загружать жанры по списку id")
    @Test
    void shouldReturnCorrectGenresByIds() {
        List<Genre> expectedGenres = mongoTemplate.findAll(Genre.class);
        Set<String> genreIds = expectedGenres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());

        Flux<Genre> genresFlux = genreRepository.findAllById(genreIds);

        List<Genre> actualGenres = genresFlux.toStream().toList();
        assertThat(actualGenres)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedGenres);
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        Flux<Genre> genresFlux = genreRepository.findAll();

        List<Genre> actualGenres = genresFlux.toStream().toList();
        assertEquals(6, actualGenres.size());
        actualGenres.forEach(System.out::println);
    }
}