package com.example.hw05jdbs.repositories;

import com.example.hw05jdbs.models.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcOperations jdbc;

    private final GnreRowMapper mapper = new GnreRowMapper();

    @Override
    public List<Genre> findAll() {
        return jdbc.query("select id, name from genres", mapper);
    }

    @Override
    public List<Genre> findAllByIds(Set<Long> ids) {
        return jdbc.query(
                "select id, name from genres where id in (:ids)",
                Map.of("ids", ids),
                mapper
        );
    }

    private static class GnreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            return new Genre()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"));
        }
    }
}
