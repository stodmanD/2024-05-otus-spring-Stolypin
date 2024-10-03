package com.example.hw08mongo.repository;

import com.example.hw08mongo.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Репозиторий на основе jpa для работы с жанрами")
@DataMongoTest
public class MongoGenreRepositoryTest {

    private final static int GENRES_COUNT = 3;

    private String idFirstGenre;

    private final static String FIRST_GENRE_NAME = "Genre_1";

    @Autowired
    private GenreRepository jpaGenreRepository;

    @BeforeEach
    public void init() {
        List<Genre> genres = jpaGenreRepository.findAll();
        idFirstGenre = genres.stream().filter(n -> n.getName().equals(FIRST_GENRE_NAME))
                .findFirst().get().getId();
        System.out.println(idFirstGenre);
    }



    @DisplayName("Должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        List<Genre> genres = jpaGenreRepository.findAll();
        assertThat(genres).isNotNull().hasSize(GENRES_COUNT)
                .allMatch(g -> !g.getName().equals(""))
                .anyMatch(g -> g.getName().equals(getGenres().get(2).getName()))
                .anyMatch(g -> g.getName().equals(getGenres().get(1).getName()))
                .anyMatch(g -> g.getName().equals(getGenres().get(0).getName()));

    }

    @DisplayName("Должен загружать жанр по id")
    @Test
    void shouldReturnCorrectGenreById() {
        Optional<Genre> genre = jpaGenreRepository.findById(idFirstGenre);
        assertThat(genre).isPresent().get()
                .matches(g -> g.getName().equals(FIRST_GENRE_NAME));
    }

    private static List<Genre> getGenres() {
        return IntStream.range(1, 4).boxed()
                .map(i -> getGenre(Integer.toString(i))).toList();
    }

    private static Genre getGenre(String id) {
        return new Genre("Genre_" + id);
    }
}
