package ru.otus.spring.repository;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findById(long id);
    List<Author> findAll();
    long count();
    Author save(Author author);
    void delete(Author author);
}
