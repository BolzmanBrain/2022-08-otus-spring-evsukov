package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;
import ru.otus.spring.dao.dto.BookDto;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Optional<Book> getById(int id);
    List<Book> getAll();
    int count();
    Integer insert(BookDto bookDto) throws ForeignKeyViolatedException;
    void update(BookDto bookDto) throws ForeignKeyViolatedException;
    void deleteById(int id);
    Book createBookFromDto(BookDto bookDto);
    BookDto extractDtoFromBook(Book book);
}
