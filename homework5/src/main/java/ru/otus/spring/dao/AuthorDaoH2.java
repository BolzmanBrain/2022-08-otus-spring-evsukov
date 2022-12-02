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
import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UniqueKeyViolatedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AuthorDaoH2 implements AuthorDao {
    private final NamedParameterJdbcOperations namedParamJdbc;
    private final JdbcOperations jdbc;

    public AuthorDaoH2(NamedParameterJdbcOperations namedParamJdbc) {
        this.namedParamJdbc = namedParamJdbc;
        this.jdbc = namedParamJdbc.getJdbcOperations();
    }

    @Override
    public Optional<Author> getById(int id) {
        var params = Map.of("id",id);
        try {
            return Optional.ofNullable(namedParamJdbc.queryForObject("select id_author,name from authors_tbl where id_author = :id", params, new AuthorMapper()));
        }
        catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> getAll() {
        return namedParamJdbc.query("select id_author,name from authors_tbl",new AuthorMapper());
    }

    @Override
    public int count() {
        Integer result = jdbc.queryForObject("select count(*) from authors_tbl",Integer.class);
        return Objects.isNull(result) ? 0 : result;
    }

    @Override
    public Integer insert(Author author) throws UniqueKeyViolatedException {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("name", author.getName());

        KeyHolder kh = new GeneratedKeyHolder();

        try {
            namedParamJdbc.update("insert into authors_tbl(name) values(:name)", sqlParameterSource, kh, new String[]{"id_author"});
        }
        catch (DuplicateKeyException e) {
            throw new UniqueKeyViolatedException(e);
        }
        return Objects.isNull(kh.getKey()) ? null : kh.getKey().intValue();
    }

    @Override
    public void update(Author author) throws UniqueKeyViolatedException {
        Map<String, Object> params = Map.of("id", author.getIdAuthor(), "name", author.getName());
        try {
            namedParamJdbc.update("update authors_tbl set name = :name where id_author = :id",params);
        }
        catch (DuplicateKeyException e) {
            throw new UniqueKeyViolatedException(e);
        }
    }

    @Override
    public void deleteById(int id) throws ForeignKeyViolatedException {
        var params = Map.of("id", id);
        try {
            namedParamJdbc.update("delete from authors_tbl where id_author = :id",params);
        }
        catch (DataIntegrityViolationException e) {
            throw new ForeignKeyViolatedException(e);
        }

    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id_author");
            String name = rs.getString("name");
            return new Author(id, name);
        }
    }
}
