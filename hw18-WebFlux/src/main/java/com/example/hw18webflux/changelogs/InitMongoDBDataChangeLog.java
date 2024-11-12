package com.example.hw18webflux.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.example.hw18webflux.models.Author;
import com.example.hw18webflux.models.Book;
import com.example.hw18webflux.models.Comment;
import com.example.hw18webflux.models.Genre;
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
    public void initAuthors(MongockTemplate mongockTemplate) {
        for (int i = 0; i < 3; i++) {
            Author author = new Author(null, "Author_" + i);
            mongockTemplate.save(author);
            authors.add(author);
        }
    }

    @ChangeSet(order = "002", id = "initGenres", author = "stodman", runAlways = true)
    public void initGenres(MongockTemplate mongockTemplate) {
        for (int i = 0; i < 6; i++) {
            Genre genre = new Genre(null, "Genre_" + i);
            mongockTemplate.save(genre);
            genres.add(genre);
        }
    }

    @ChangeSet(order = "003", id = "initBooks", author = "stodman", runAlways = true)
    public void initBooks(MongockTemplate mongockTemplate) {
        for (int i = 0; i < 3; i++) {
            Book book = new Book(null, "Title_" + i, authors.get(i), genres.subList(i, i + 2));
            mongockTemplate.save(book);
            books.add(book);
        }
    }

    @ChangeSet(order = "004", id = "initComments", author = "stodman", runAlways = true)
    public void initComments(MongockTemplate mongockTemplate) {
        for (int i = 0; i < 3; i++) {
            Comment comment = new Comment(null, "Comment_" + i, books.get(0));
            mongockTemplate.save(comment);
        }
    }

}
