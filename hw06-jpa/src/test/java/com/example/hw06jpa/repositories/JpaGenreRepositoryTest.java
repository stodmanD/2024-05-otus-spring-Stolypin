package com.example.hw06jpa.repositories;

import com.example.hw06jpa.models.Genre;
import com.example.hw06jpa.repositories.impl.JpaGenreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import({JpaGenreRepository.class})
class JpaGenreRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaGenreRepository jpaGenreRepository;

    @DisplayName("Should return correct genres by id")
    @Test
    void shouldReturnCorrectGenresByIds() {
        Genre genre1 = em.find(Genre.class, 1L);
        Genre genre2 = em.find(Genre.class, 2L);
        Genre genre3 = em.find(Genre.class, 3L);
        List<Genre> expectedGenres = List.of(genre1, genre2, genre3);

        List<Genre> actualGenres = jpaGenreRepository.findAllByIds(Set.of(1L, 2L, 3L));

        assertThat(actualGenres)
                .usingRecursiveComparison()
                .isEqualTo(expectedGenres);
    }

    @DisplayName("Should return correct genres list")
    @Test
    void shouldReturnCorrectGenresList() {
        List<Genre> genres = jpaGenreRepository.findAll();

        assertEquals(6, genres.size());
        genres.forEach(System.out::println);
    }
}