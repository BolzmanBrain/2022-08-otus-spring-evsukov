package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UniqueKeyViolatedException;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> getById(int id);
    List<Author> getAll();
    int count();
    Integer insert(Author author) throws UniqueKeyViolatedException;
    void update(Author author) throws UniqueKeyViolatedException;
    void deleteById(int id) throws ForeignKeyViolatedException;
}
