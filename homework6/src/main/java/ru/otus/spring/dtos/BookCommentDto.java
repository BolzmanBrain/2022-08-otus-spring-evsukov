package ru.otus.spring.dtos;

import lombok.Data;

@Data
public class BookCommentDto {
    private final Long id;
    private final String text;
    private final Long idBook;

    public static BookCommentDto of(String text, Long idBook) {
        return new BookCommentDto(null, text, idBook);
    }
}
