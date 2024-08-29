package com.example.hw07springdata.repositories;

import com.example.hw07springdata.models.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе SpringDataJpa для работы с жанрами ")
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("должен загружать жанры по списку id")
    @Test
    void shouldReturnCorrectGenresByIds() {
        Genre genre1 = em.find(Genre.class, 1L);
        Genre genre2 = em.find(Genre.class, 2L);
        Genre genre3 = em.find(Genre.class, 3L);
        List<Genre> expectedGenres = List.of(genre1, genre2, genre3);

        List<Genre> actualGenres = genreRepository.findAllById(Set.of(1L, 2L, 3L));

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