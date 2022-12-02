package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UniqueKeyViolatedException;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Optional<Genre> getById(int id);
    List<Genre> getAll();
    int count();
    Integer insert(Genre genre) throws UniqueKeyViolatedException;
    void update(Genre genre) throws UniqueKeyViolatedException;
    void deleteById(int id) throws ForeignKeyViolatedException;
}
