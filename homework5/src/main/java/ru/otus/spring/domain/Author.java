package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Author {
    private final Integer idAuthor;
    private final String name;

    public static Author createWithName(String name) {
        return new Author(null,name);
    }
    public static Author createWithId(int idAuthor) {
        return new Author(idAuthor, null);
    }
}
