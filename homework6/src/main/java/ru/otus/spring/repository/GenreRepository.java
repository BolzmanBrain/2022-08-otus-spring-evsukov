package ru.otus.spring.repository;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> findById(long id);
    List<Genre> findAll();
    long count();
    Genre save(Genre genre);
    void delete(Genre genre);
}
