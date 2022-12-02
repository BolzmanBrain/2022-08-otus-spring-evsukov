package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Genre {
    private final Integer idGenre;
    private final String name;

    public static Genre createWithName(String name) {
        return new Genre(null,name);
    }
    public static Genre createWithId(int idGenre) {
        return new Genre(idGenre, null);
    }
}
