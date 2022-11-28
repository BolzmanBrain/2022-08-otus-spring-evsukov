package ru.otus.spring.presentation;

import ru.otus.spring.domain.Genre;

public interface GenreToStringConvertor {
    String convertToString(Genre genre);
}
