package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> findById(long id);
    List<Author> findAll();
    long count();
    Author save(Author author);
    void deleteById(long id);
}
