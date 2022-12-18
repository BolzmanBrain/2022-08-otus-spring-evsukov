package ru.otus.spring.services;

import ru.otus.spring.domain.Book;
import ru.otus.spring.dtos.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findById(String id);
    List<Book> findAll();
    long count();
    Book save(BookDto bookDto);
    void deleteById(String id);
}
