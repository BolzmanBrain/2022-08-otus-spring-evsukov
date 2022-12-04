package ru.otus.spring.repository;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> getById(long id);
    List<Genre> getAll();
    long count();
    Genre save(Genre genre);
    void deleteById(long id);
}
