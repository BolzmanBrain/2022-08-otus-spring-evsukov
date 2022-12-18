package ru.otus.spring.repository;

import ru.otus.spring.domain.Genre;

public interface GenreRepositoryCustom {
    Genre saveEnsureConsistency(Genre genre);
    void deleteEnsureConsistencyById(String id);
}
