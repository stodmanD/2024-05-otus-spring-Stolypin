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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    private final List<Author> authors = Arrays.asList(new Author("Author_1"),
            new Author("Author_2"),
            new Author("Author_3"));

    private final List<Genre> genres = Arrays.asList(new Genre("Genre_1"),
            new Genre("Genre_2"),
            new Genre("Genre_3"));

    private List<Book> books = new ArrayList<>();

    @ChangeSet(order = "001", id = "insertAuthors", author = "bev")
    public void insertAuthors(AuthorRepository repository) {
        authors.forEach(repository::save);
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "bev")
    public void insertGenres(GenreRepository repository) {
        genres.forEach(repository::save);
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "bev")
    public void insertBooks(BookRepository repository) {
        books.add(new Book("Title_1", authors.get(0), genres.get(0)));
        books.add(new Book("Title_2", authors.get(1), genres.get(1)));
        books.add(new Book("Title_3", authors.get(2), genres.get(2)));
        books.forEach(repository::save);
    }

    @ChangeSet(order = "004", id = "insertComments", author = "bev")
    public void insertComments(CommentRepository repository) {
        repository.save(new Comment("Comment_Book1_ 1", books.get(0)));
        repository.save(new Comment("Comment_Book1_ 2", books.get(0)));
        repository.save(new Comment("Comment_Book2_ 1", books.get(1)));
        repository.save(new Comment("Comment_Book3_ 1", books.get(2)));
    }
}
