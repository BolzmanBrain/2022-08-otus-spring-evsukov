package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Optional<Book> getById(int id);
    List<Book> getAll();
    int count();
    Integer insert(Book book);
    void update(Book book);
    void deleteById(int id);
}
