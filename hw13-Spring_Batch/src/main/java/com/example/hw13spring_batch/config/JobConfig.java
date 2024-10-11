package com.example.hw13spring_batch.config;

import com.example.hw13spring_batch.cache.AuthorCache;
import com.example.hw13spring_batch.cache.GenreCache;
import com.example.hw13spring_batch.models.jpa.AuthorJpa;
import com.example.hw13spring_batch.models.jpa.BookJpa;
import com.example.hw13spring_batch.models.jpa.GenreJpa;
import com.example.hw13spring_batch.models.mongo.Author;
import com.example.hw13spring_batch.models.mongo.Book;
import com.example.hw13spring_batch.models.mongo.Genre;
import com.example.hw13spring_batch.service.AuthorMongoToSqlTransformer;
import com.example.hw13spring_batch.service.BookMongoToSqlTransformer;
import com.example.hw13spring_batch.service.CleanUpService;
import com.example.hw13spring_batch.service.GenreMongoToSqlTransformer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoPagingItemReader;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;

@SuppressWarnings("unused")
@RequiredArgsConstructor
@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 5;

    private static final String JOB_NAME = "migrateBooksJob";

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final AuthorCache authorsDic;

    private final GenreCache genresDic;

    @Bean
    public MongoPagingItemReader<Author> authorReader(MongoTemplate template) {
        return new MongoPagingItemReaderBuilder<Author>()
                .name("authorMongoReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Author.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public MongoPagingItemReader<Genre> genreReader(MongoTemplate template) {
        return new MongoPagingItemReaderBuilder<Genre>()
                .name("genreMongoReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Genre.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public MongoPagingItemReader<Book> bookReader(MongoTemplate template) {
        return new MongoPagingItemReaderBuilder<Book>()
                .name("bookMongoReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(Book.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public AuthorMongoToSqlTransformer authorMongoToSqlTransformer() {
        return new AuthorMongoToSqlTransformer(authorsDic);
    }

    @Bean
    public GenreMongoToSqlTransformer genreMongoToSqlTransformer() {
        return new GenreMongoToSqlTransformer(genresDic);
    }

    @Bean
    public BookMongoToSqlTransformer bookMongoToSqlTransformer() {
//        return new BookMongoToSqlTransformer(authorsDic, bookDic,genresDic);
        return new BookMongoToSqlTransformer(authorsDic, genresDic);
    }

    @Bean
    public ItemProcessor<Author, AuthorJpa> authorProcessor(AuthorMongoToSqlTransformer authorTransformer) {
        return authorTransformer::transform;
    }

    @Bean
    public ItemProcessor<Genre, GenreJpa> genreProcessor(GenreMongoToSqlTransformer genreTransformer) {
        return genreTransformer::transform;
    }

    @Bean
    public ItemProcessor<Book, BookJpa> bookProcessor(BookMongoToSqlTransformer bookTransformer) {
        return bookTransformer::transform;
    }

    @Bean
    public JpaItemWriter<AuthorJpa> authorWriter(EntityManager entityManager) {
        return new JpaItemWriterBuilder<AuthorJpa>()
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .build();
    }

    @Bean
    public JpaItemWriter<GenreJpa> genreWriter(EntityManager entityManager) {
        return new JpaItemWriterBuilder<GenreJpa>()
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .build();
    }

    @Bean
    public JpaItemWriter<BookJpa> bookWriter(EntityManager entityManager) {
        return new JpaItemWriterBuilder<BookJpa>()
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter cleanUpTasklet(CleanUpService cleanUpService) {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(cleanUpService);
        adapter.setTargetMethod("cleanUp");

        return adapter;
    }

    @Bean
    public Step transformAuthorsStep(ItemReader<Author> reader, JpaItemWriter<AuthorJpa> writer,
                                   ItemProcessor<Author, AuthorJpa> itemProcessor) {
        return new StepBuilder("transformAuthorsStep", jobRepository)
                .<Author, AuthorJpa>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformGenresStep(ItemReader<Genre> reader, JpaItemWriter<GenreJpa> writer,
                                   ItemProcessor<Genre, GenreJpa> itemProcessor) {
        return new StepBuilder("transformGenresStep", jobRepository)
                .<Genre, GenreJpa>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformBooksStep(ItemReader<Book> reader, JpaItemWriter<BookJpa> writer,
                                   ItemProcessor<Book, BookJpa> itemProcessor) {
        return new StepBuilder("transformBooksStep", jobRepository)
                .<Book, BookJpa>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job migrateBooksJob(Step transformBooksStep,
                               Step transformAuthorsStep,
                               Step transformGenresStep,
                               Step cleanUpStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorsStep)
                .next(transformGenresStep)
                .next(transformBooksStep)
                .next(cleanUpStep)
                .end()
                .build();
    }

    @Bean
    public Step cleanUpStep(CleanUpService cleanUpService) {
        return new StepBuilder("cleanUpStep", jobRepository)
                .tasklet(cleanUpTasklet(cleanUpService), platformTransactionManager)
                .build();
    }
}
