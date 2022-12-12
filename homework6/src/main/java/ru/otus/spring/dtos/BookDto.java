package ru.otus.spring.dtos;

import lombok.Data;

@Data
public class BookDto {
    private final Long id;
    private final String name;
    private final Long idAuthor;
    private final Long idGenre;

    public static BookDto of(String name, Long idAuthor, Long idGenre) {
        return new BookDto(null, name, idAuthor, idGenre);
    }
}
