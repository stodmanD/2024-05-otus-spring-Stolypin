package com.example.hw05jdbs.repositories;

import com.example.hw05jdbs.exceptions.EntityNotFoundException;
import com.example.hw05jdbs.models.Author;
import com.example.hw05jdbs.models.Book;
import com.example.hw05jdbs.models.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;

    private final GenreRepository genreRepository;

    private final BookRowMapper mapper = new BookRowMapper();

    @Override
    public Optional<Book> findById(long id) {
        final var book = jdbc.query("""
            select b.title book_title, b.id book_id, a.full_name author_name, a.id author_id
            from books b join authors a on b.author_id = a.id
            where b.id = :id
                """,
                Map.of("id", id),
                mapper
        ).stream().findAny();

        book.ifPresent(this::enrichWithGenres);
        return book;
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (0 == book.getId()) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    private List<Book> getAllBooksWithoutGenres() {
        return jdbc.query("""
            select b.title book_title, b.id book_id, a.full_name author_name, a.id author_id
            from books b join authors a on b.author_id = a.id
                """,
                mapper
        );
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return jdbc.query(
                "select book_id, genre_id from books_genres",
                (rs, i) -> new BookGenreRelation(
                        rs.getLong("book_id"),
                        rs.getLong("genre_id")
                )
        );
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        final var bookIdGenresMapping = booksWithoutGenres.stream()
                .collect(Collectors.toMap(Book::id, Book::genres));

        final var genresIdMapping = genres.stream()
                .collect(Collectors.toMap(Genre::id, Function.identity()));

        for (var relation : relations) {
            final var bookGenresList = bookIdGenresMapping.get(relation.bookId());
            final var genre = genresIdMapping.get(relation.genreId());
            if (null != genre && null != bookGenresList) {
                bookGenresList.add(genre);
            }
         }
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        jdbc.update(
                "insert into books (title, author_id) values (:title,  :author_id)",
                new MapSqlParameterSource()
                        .addValue("title", book.title())
                        .addValue("author_id", book.author().id()),
                keyHolder,
                new String[]{ "id" }
        );

        book.id(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        final var rowsUpdated = jdbc.update(
                "update books set title = :title, author_id = :author_id where id = :id",
                new MapSqlParameterSource()
                        .addValue("id", book.id())
                        .addValue("title", book.title())
                        .addValue("author_id", book.author().id())
        );

        if (0 == rowsUpdated) {
            throw new EntityNotFoundException("Book not found, id = " + book.id());
        }

        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        final var paramMaps =  book.genres().stream()
                .map(Genre::id)
                .map(genreId -> new MapSqlParameterSource()
                        .addValue("genre_id", genreId)
                        .addValue("book_id", book.id())
                ).toArray(MapSqlParameterSource[]::new);
        jdbc.batchUpdate(
                "insert into books_genres (book_id, genre_id) values (:book_id, :genre_id)",
                paramMaps
        );
    }

    private void removeGenresRelationsFor(Book book) {
        jdbc.update(
                "delete from books_genres where book_id = :book_id",
                Map.of("book_id", book.id())
        );
    }

    private void enrichWithGenres(Book book) {
        final var relations = getGenreRelationsForBookId(book.id());
        final var genreIds = relations.stream()
                .map(BookGenreRelation::genreId).collect(Collectors.toSet());
        book.genres(genreRepository.findAllByIds(genreIds));
    }

    private List<BookGenreRelation> getGenreRelationsForBookId(Long bookId) {
        return jdbc.query(
                "select book_id, genre_id from books_genres where book_id = :book_id",
                Map.of("book_id", bookId),
                (rs, i) -> new BookGenreRelation(
                        rs.getLong("book_id"),
                        rs.getLong("genre_id")
                )
        );
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book().id(rs.getLong("book_id"))
                    .title(rs.getString("book_title"))
                    .author(new Author()
                            .id(rs.getLong("author_id"))
                            .fullName(rs.getString("author_name"))
                    );
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }
}
