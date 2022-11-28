package ru.otus.spring.presentation;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;

@Component
public class AuthorToStringConvertorAsObject implements AuthorToStringConvertor {
    @Override
    public String convertToString(Author author) {
        return author.toString();
    }
}
