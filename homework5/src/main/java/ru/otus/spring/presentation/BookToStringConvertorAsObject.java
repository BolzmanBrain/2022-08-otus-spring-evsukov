package ru.otus.spring.presentation;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;

@Component
public class BookToStringConvertorAsObject implements BookToStringConvertor {
    @Override
    public String convertToString(Book book) {
        return book.toString();
    }
}
