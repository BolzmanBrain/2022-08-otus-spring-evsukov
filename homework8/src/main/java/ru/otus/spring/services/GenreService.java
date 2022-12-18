package ru.otus.spring.services;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Optional<Genre> findById(String id);
    List<Genre> findAll();
    long count();
    Genre save(Genre genre);
    void deleteById(String id);
}
