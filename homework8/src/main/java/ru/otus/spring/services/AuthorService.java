package ru.otus.spring.services;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> findById(String id);
    List<Author> findAll();
    long count();
    Author save(Author author);
    void deleteById(String id);
}
