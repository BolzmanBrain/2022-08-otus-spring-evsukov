package ru.otus.spring.repository;

import ru.otus.spring.domain.Author;

public interface AuthorRepositoryCustom {
    Author saveEnsureConsistency(Author author);
    void deleteEnsureConsistencyById(String id);
}
