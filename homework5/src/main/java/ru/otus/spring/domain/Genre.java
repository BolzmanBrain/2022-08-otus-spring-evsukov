package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Genre {
    private final Integer idGenre;
    private final String name;

    public static Genre createWithNullId(String name) {
        return new Genre(null,name);
    }
}
