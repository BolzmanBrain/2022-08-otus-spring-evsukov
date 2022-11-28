package ru.otus.spring.presentation;

import ru.otus.spring.domain.Author;

public interface AuthorToStringConvertor {
    String convertToString(Author author);
}
