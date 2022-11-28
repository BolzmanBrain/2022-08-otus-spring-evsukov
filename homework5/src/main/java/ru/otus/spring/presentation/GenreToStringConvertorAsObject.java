package ru.otus.spring.presentation;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Genre;

@Component
public class GenreToStringConvertorAsObject implements GenreToStringConvertor {
    @Override
    public String convertToString(Genre genre) {
        return genre.toString();
    }
}
