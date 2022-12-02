package ru.otus.spring.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UniqueKeyViolatedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class GenreDaoH2 implements GenreDao {
    private final NamedParameterJdbcOperations namedParamJdbc;
    private final JdbcOperations jdbc;

    public GenreDaoH2(NamedParameterJdbcOperations namedParamJdbc) {
        this.namedParamJdbc = namedParamJdbc;
        this.jdbc = namedParamJdbc.getJdbcOperations();
    }

    @Override
    public Optional<Genre> getById(int id) {
        var params = Map.of("id",id);
        try {
            return Optional.ofNullable(namedParamJdbc.queryForObject("select id_genre,name from genres_tbl where id_genre = :id", params, new GenreMapper()));
        }
        catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> getAll() {
        return namedParamJdbc.query("select id_genre,name from genres_tbl",new GenreMapper());
    }

    @Override
    public int count() {
        Integer result = jdbc.queryForObject("select count(*) from genres_tbl",Integer.class);
        return Objects.isNull(result) ? 0 : result;
    }

    @Override
    public Integer insert(Genre genre) throws UniqueKeyViolatedException {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("name", genre.getName());

        KeyHolder kh = new GeneratedKeyHolder();
        try {
            namedParamJdbc.update("insert into genres_tbl(name) values(:name)",sqlParameterSource, kh, new String[]{"id_genre"});
        }
        catch (DuplicateKeyException e) {
            throw new UniqueKeyViolatedException(e);
        }
        return Objects.isNull(kh.getKey()) ? null : kh.getKey().intValue();
    }

    @Override
    public void update(Genre genre) throws UniqueKeyViolatedException {
        Map<String,Object> params = Map.of("id", genre.getIdGenre(), "name", genre.getName());
        try {
            namedParamJdbc.update("update genres_tbl set name = :name where id_genre = :id", params);
        }
        catch (DuplicateKeyException e) {
            throw new UniqueKeyViolatedException(e);
        }
    }

    @Override
    public void deleteById(int id) throws ForeignKeyViolatedException {
        var params = Map.of("id", id);
        try {
            namedParamJdbc.update("delete from genres_tbl where id_genre = :id", params);
        }
        catch (DataIntegrityViolationException e) {
            throw new ForeignKeyViolatedException(e);
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id_genre");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
