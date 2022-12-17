package ru.otus.spring.dtos;

import lombok.Data;

@Data
public class BookDto {
    private final String id;
    private final String name;
    private final String idAuthor;
    private final String idGenre;

    public static BookDto createForInsert(String name, String idAuthor, String idGenre) {
        return new BookDto(null, name, idAuthor, idGenre);
    }
}
