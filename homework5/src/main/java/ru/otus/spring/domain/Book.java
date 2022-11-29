package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Book {
    private final Integer idBook;
    private final String name;
    private final Author author;
    private final Genre genre;

    public static Book createWithoutId(String name, Author author, Genre genre) {
        return new Book(null, name, author, genre);
    }
}
