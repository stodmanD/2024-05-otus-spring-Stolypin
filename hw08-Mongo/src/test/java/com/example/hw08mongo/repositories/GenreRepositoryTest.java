package com.example.hw08mongo.repositories;

import com.example.hw08mongo.models.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе SpringDataMongoDb для работы с жанрами ")
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

        List<Genre> actualGenres = genreRepository.findAllById(genreIds);

        assertThat(actualGenres)
                .usingRecursiveComparison()
                .isEqualTo(expectedGenres);
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        List<Genre> genres = genreRepository.findAll();

        assertEquals(6, genres.size());
        genres.forEach(System.out::println);
    }
}