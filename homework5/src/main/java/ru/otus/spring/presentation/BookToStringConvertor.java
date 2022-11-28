package ru.otus.spring.presentation;

import ru.otus.spring.domain.Book;

public interface BookToStringConvertor {
    String convertToString(Book book);
}
