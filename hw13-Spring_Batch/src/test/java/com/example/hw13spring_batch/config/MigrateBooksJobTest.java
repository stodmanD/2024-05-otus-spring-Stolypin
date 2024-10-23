package com.example.hw13spring_batch.config;

import com.example.hw13spring_batch.models.jpa.AuthorJpa;
import com.example.hw13spring_batch.models.jpa.BookJpa;
import com.example.hw13spring_batch.models.jpa.GenreJpa;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBatchTest
@SpringBootTest
@AutoConfigureDataJpa
@Transactional(propagation = Propagation.SUPPORTS)
class MigrateBooksJobTest {
    private static final String JOB_NAME = "migrateBooksJob";
    private static final String BOOKS_REF_PATH = "/config/migrateBookJob/books.json";
    private static final String AUTHORS_REF_PATH = "/config/migrateBookJob/authors.json";
    private static final String GENRES_REF_PATH = "/config/migrateBookJob/genres.json";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private EntityManager em;

    private static ObjectMapper MAPPER;

    @BeforeAll
    static void initStatic() {
        MAPPER = new ObjectMapper();
    }

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(JOB_NAME);

        JobParameters parameters = new JobParametersBuilder()
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        List<AuthorJpa> authors =
                em.createQuery("select a from AuthorJpa a", AuthorJpa.class).getResultList();
        List<GenreJpa> genres =
                em.createQuery("select g from GenreJpa g", GenreJpa.class).getResultList();
        List<BookJpa> books =
                em.createQuery("select b from BookJpa b", BookJpa.class).getResultList();

        List<AuthorJpa> refAuthors = MAPPER.readValue(getFileContent(AUTHORS_REF_PATH), new TypeReference<>() {});
        List<GenreJpa> refGenres = MAPPER.readValue(getFileContent(GENRES_REF_PATH), new TypeReference<>() {});
        List<BookJpa> refBooks = MAPPER.readValue(getFileContent(BOOKS_REF_PATH), new TypeReference<>() {});

        assertAll(
                () -> assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED"),
                () -> assertThat(authors).usingRecursiveComparison().isEqualTo(refAuthors),
                () -> assertThat(genres).usingRecursiveComparison().isEqualTo(refGenres),
                () -> assertThat(books).usingRecursiveComparison().isEqualTo(refBooks));
    }

    private String getFileContent(String path) throws Exception {
        URI fileUri = getClass().getResource(path).toURI();
        List<String> strings = Files.readAllLines(Paths.get(fileUri));
        return String.join("", strings);
    }
}