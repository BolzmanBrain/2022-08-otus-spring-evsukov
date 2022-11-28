package ru.otus.spring.dao.dto;

import lombok.Data;

@Data
public class BookDto {
    private final Integer idBook;
    private final String name;
    private final Integer idAuthor;
    private final Integer idGenre;

    public static BookDto createWithNullId(String name, Integer idAuthor, Integer idGenre) {
        return new BookDto(null,name, idAuthor, idGenre);
    }
}
