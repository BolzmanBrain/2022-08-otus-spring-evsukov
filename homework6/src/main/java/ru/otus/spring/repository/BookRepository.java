package ru.otus.spring.repository;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(long id);
    List<Book> findAll();
    long count();
    Book save(Book book);
    void delete(Book book);
}
