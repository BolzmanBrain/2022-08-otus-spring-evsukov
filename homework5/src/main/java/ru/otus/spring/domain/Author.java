package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Author {
    private final Integer idAuthor;
    private final String name;

    public static Author createWithNullId(String name) {
        return new Author(null,name);
    }
}
