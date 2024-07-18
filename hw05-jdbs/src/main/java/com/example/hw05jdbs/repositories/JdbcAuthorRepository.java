package com.example.hw05jdbs.repositories;

import com.example.hw05jdbs.models.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcAuthorRepository implements AuthorRepository {

    private final NamedParameterJdbcOperations jdbc;

    private final AuthorRowMapper mapper = new AuthorRowMapper();

    @Override
    public List<Author> findAll() {
        return jdbc.query("select id, full_name from Authors", mapper);
    }

    @Override
    public Optional<Author> findById(long id) {
        return jdbc.query(
                "select id, full_name from Authors where id = :id",
                Map.of("id", id),
                mapper
        ).stream().findAny();
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            return new Author()
                    .id(rs.getLong("id"))
                    .fullName(rs.getString("full_name"));
        }
    }
}
