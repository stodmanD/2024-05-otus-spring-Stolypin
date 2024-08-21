package com.example.hw08mongo.changelogs;

import com.example.hw08mongo.models.Author;
import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.models.Comment;
import com.example.hw08mongo.models.Genre;
import com.example.hw08mongo.repositories.AuthorRepository;
import com.example.hw08mongo.repositories.BookRepository;
import com.example.hw08mongo.repositories.CommentRepository;
import com.example.hw08mongo.repositories.GenreRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;

import java.util.LinkedList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private final List<Author> authors = new LinkedList<>();

    private final List<Genre> genres = new LinkedList<>();

    private final List<Book> books = new LinkedList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "shdv09", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "shdv09", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        for (int i = 0; i < 3; i++) {
            Author author = new Author(null, "author" + i);
            repository.save(author);
            authors.add(author);
        }
    }

    @ChangeSet(order = "002", id = "initGenres", author = "shdv09", runAlways = true)
    public void initGenres(GenreRepository repository) {
        for (int i = 0; i < 6; i++) {
            Genre genre = new Genre(null, "genre" + i);
            repository.save(genre);
            genres.add(genre);
        }
    }

    @ChangeSet(order = "003", id = "initBooks", author = "shdv09", runAlways = true)
    public void initBooks(BookRepository repository) {
        for (int i = 0; i < 3; i++) {
            Book book = new Book(null, "book" + i, authors.get(i), genres.subList(i, i + 2));
            repository.save(book);
            books.add(book);
        }
    }

    @ChangeSet(order = "004", id = "initComments", author = "shdv09", runAlways = true)
    public void initComments(CommentRepository repository) {
        for (int i = 0; i < 3; i++) {
            Comment comment = new Comment(null, "comment" + i, books.get(0));
            repository.save(comment);
        }
    }
}
