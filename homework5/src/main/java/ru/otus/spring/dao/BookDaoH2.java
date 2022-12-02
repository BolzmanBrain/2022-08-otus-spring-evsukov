package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookDaoH2 implements BookDao {
    private final NamedParameterJdbcOperations namedParamJdbc;

    private final static String SELECT_BOOKS_SQL = "select b.id_book,b.name as book_name,b.id_author,b.id_genre, " +
            "a.name as author_name, g.name as genre_name " +
            "from books_tbl b " +
            "join authors_tbl a on a.id_author = b.id_author " +
            "join genres_tbl g on g.id_genre = b.id_genre";

    @Override
    public Optional<Book> getById(int id) {
        try {
            var params = Map.of("id_book",id);
            Book book = namedParamJdbc.queryForObject( SELECT_BOOKS_SQL +
                            " where id_book = :id_book",
                    params, new BookMapper());
            return Optional.ofNullable(book);
        }
        catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        return namedParamJdbc.query(SELECT_BOOKS_SQL,
                new BookMapper())
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        JdbcOperations jdbc = namedParamJdbc.getJdbcOperations();
        Integer result = jdbc.queryForObject("select count(*) from books_tbl",Integer.class);
        return Objects.isNull(result) ? 0 : result;
    }

    @Override
    public Integer insert(Book book) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("name",  book.getName());
        paramSource.addValue("id_author", book.getAuthor().getIdAuthor());
        paramSource.addValue("id_genre", book.getGenre().getIdGenre());

        KeyHolder kh = new GeneratedKeyHolder();

        try {
            namedParamJdbc.update("insert into books_tbl(name,id_author,id_genre) values(:name,:id_author,:id_genre)",
                    paramSource, kh, new String[]{"id_book"});
        }
        catch (DataIntegrityViolationException e) {
            throw new ForeignKeyViolatedException(e);
        }
        return Objects.isNull(kh.getKey()) ? null : kh.getKey().intValue();
    }

    @Override
    public void update(Book book) {
        var params = new HashMap<String,Object>();
        params.put("id_book", book.getIdBook());
        params.put("name", book.getName());
        params.put("id_author", book.getAuthor().getIdAuthor());
        params.put("id_genre", book.getGenre().getIdGenre());

        try {
            namedParamJdbc.update("update books_tbl set name = :name, id_author = :id_author,id_genre = :id_genre where id_book = :id_book",
                    params);
        }
        catch (DataIntegrityViolationException e) {
            throw new ForeignKeyViolatedException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        var params = Map.of("id", id);
        namedParamJdbc.update("delete from books_tbl where id_book = :id",
                params);
    }

    static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            int idAuthor = rs.getInt("id_author");
            String authorName = rs.getString("author_name");
            Author author= new Author(idAuthor, authorName);

            int idGenre = rs.getInt("id_genre");
            String genreName = rs.getString("genre_name");
            Genre genre = new Genre(idGenre, genreName);

            int idBook = rs.getInt("id_book");
            String bookName = rs.getString("book_name");
            return new Book(idBook, bookName, author, genre);
        }
    }
}
