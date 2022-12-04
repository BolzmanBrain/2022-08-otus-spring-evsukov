package ru.otus.spring.repository;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> getById(long id);
    List<Book> getAll();
    long count();
    Book save(Book book);
    void deleteById(long id);
}
