package com.example.hw08mongo.changelog;

import com.example.hw08mongo.models.Author;
import com.example.hw08mongo.models.Book;
import com.example.hw08mongo.models.Comment;
import com.example.hw08mongo.models.Genre;
import com.example.hw08mongo.repository.AuthorRepository;
import com.example.hw08mongo.repository.BookRepository;
import com.example.hw08mongo.repository.CommentRepository;
import com.example.hw08mongo.repository.GenreRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;


import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    private List<Author> authors = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();

    private List<Book> books = new ArrayList<>();

    @ChangeSet(order = "001", id = "drop DB", author = "bev", runAlways = true)
    public void dropDatabase(MongoDatabase mongoDatabase) {
        mongoDatabase.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "bev")
    public void insertAuthors(AuthorRepository repository) {
        authors.add(repository.save(new Author("Author_1")));
        authors.add(repository.save(new Author("Author_2")));
        authors.add(repository.save(new Author("Author_3")));
    }
    @ChangeSet(order = "003", id = "insertGenres", author = "bev")
    public void insertGenres(GenreRepository repository) {
        genres.add(repository.save(new Genre("Genre_1")));
        genres.add(repository.save(new Genre("Genre_2")));
        genres.add(repository.save(new Genre("Genre_3")));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "bev")
    public void insertBooks(BookRepository repository) {
        books.add(new Book("Title_1", authors.get(0), genres.get(0)));
        books.add(new Book("Title_2", authors.get(1), genres.get(1)));
        books.add(new Book("Title_3", authors.get(2), genres.get(2)));
        books.add(new Book("Comment_TTL_1", authors.get(0), genres.get(0)));
        books.add(new Book("Comment_TTL_2", authors.get(1), genres.get(1)));
        books.add(new Book("Comment_TTL_3", authors.get(2), genres.get(2)));
        books.forEach(repository::save);
    }

    @ChangeSet(order = "005", id = "insertComments", author = "bev")
    public void insertComments(CommentRepository repository) {
        Book book = books.stream()
                .filter(b -> b.getTitle().equals("Comment_TTL_1")).findFirst().get();
        repository.save(new Comment("Comment_Book1_ 1", book));
        repository.save(new Comment( "Comment_Book1_ 2", book));
    }
}
