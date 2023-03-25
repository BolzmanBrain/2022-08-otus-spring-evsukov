package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findById(long id);
    List<Book> findAll();
    long count();
    Book save(BookDto bookDto);
    void deleteById(long id);
}
