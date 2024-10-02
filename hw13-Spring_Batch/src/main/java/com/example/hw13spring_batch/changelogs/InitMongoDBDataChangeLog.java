package com.example.hw13spring_batch.changelogs;

import com.example.hw13spring_batch.models.mongo.Author;
import com.example.hw13spring_batch.models.mongo.Book;
import com.example.hw13spring_batch.models.mongo.Genre;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;

import java.util.LinkedList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private final List<Author> authors = new LinkedList<>();

    private final List<Genre> genres = new LinkedList<>();

    private final List<Book> books = new LinkedList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "stodman", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "stodman", runAlways = true)
    public void initAuthors(MongockTemplate template) {
        for (int i = 0; i < 20; i++) {
            Author author = new Author(null, "author" + i);
            template.save(author);
            authors.add(author);
        }
    }

    @ChangeSet(order = "002", id = "initGenres", author = "stodman", runAlways = true)
    public void initGenres(MongockTemplate template) {
        for (int i = 0; i < 15; i++) {
            Genre genre = new Genre(null, "genre" + i);
            template.save(genre);
            genres.add(genre);
        }
    }

    @ChangeSet(order = "003", id = "initBooks", author = "stodman", runAlways = true)
    public void initBooks(MongockTemplate template) {
        for (int i = 0; i < 3; i++) {
            Book book = new Book(null, "book" + i, authors.get(i), genres.subList(i, i + 2));
            template.save(book);
            books.add(book);
        }
    }
}
