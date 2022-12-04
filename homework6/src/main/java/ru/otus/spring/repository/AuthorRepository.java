package ru.otus.spring.repository;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> getById(long id);
    List<Author> getAll();
    long count();
    Author save(Author author);
    void deleteById(long id);
}
