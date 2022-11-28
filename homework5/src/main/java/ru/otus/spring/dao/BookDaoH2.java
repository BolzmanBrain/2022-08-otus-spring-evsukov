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
import ru.otus.spring.dao.dto.BookDto;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookDaoH2 implements BookDao {
    private final NamedParameterJdbcOperations namedParamJdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Optional<Book> getById(int id) {
        var params = Map.of("id_book",id);
        BookDto dto;
        try {
            dto = namedParamJdbc.queryForObject("select id_book,name,id_author,id_genre from books_tbl where id_book = :id_book",
                    params, new BookDtoMapper());
        }
        catch (DataAccessException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(dto).map(this::createBookFromDto);
    }

    @Override
    public List<Book> getAll() {
        return namedParamJdbc.query("select id_book,name,id_author,id_genre from books_tbl",
                new BookDtoMapper())
                .stream()
                .map(this::createBookFromDto)
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
    public Integer insert(BookDto bookDto) throws ForeignKeyViolatedException {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("name",  bookDto.getName());
        paramSource.addValue("id_author", bookDto.getIdAuthor());
        paramSource.addValue("id_genre", bookDto.getIdGenre());

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
    public void update(BookDto bookDto) throws ForeignKeyViolatedException {
        var params = new HashMap<String,Object>();
        params.put("id_book", bookDto.getIdBook());
        params.put("name", bookDto.getName());
        params.put("id_author", bookDto.getIdAuthor());
        params.put("id_genre", bookDto.getIdGenre());

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

    @Override
    public Book createBookFromDto(BookDto dto) {
        Author author;
        Genre genre;
        try {
            author = authorDao.getById(dto.getIdAuthor()).orElseThrow();
            genre = genreDao.getById(dto.getIdGenre()).orElseThrow();
        }
        catch (Exception e) {
            return null;
        }
        return new Book(dto.getIdBook(), dto.getName(), author, genre);
    }

    @Override
    public BookDto extractDtoFromBook(Book book) {
        return new BookDto(book.getIdBook(), book.getName(), book.getAuthor().getIdAuthor(), book.getGenre().getIdGenre());
    }

    static class BookDtoMapper implements RowMapper<BookDto> {
        @Override
        public BookDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            int idBook = rs.getInt("id_book");
            String name = rs.getString("name");
            int idAuthor = rs.getInt("id_author");
            int idGenre = rs.getInt("id_genre");
            return new BookDto(idBook, name, idAuthor, idGenre);
        }
    }
}
