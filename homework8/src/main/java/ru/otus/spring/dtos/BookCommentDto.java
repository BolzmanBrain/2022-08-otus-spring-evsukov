package ru.otus.spring.dtos;

import lombok.Data;

@Data
public class BookCommentDto {
    private final String id;
    private final String text;
    private final String idBook;

    public static BookCommentDto createForInsert(String text, String idBook) {
        return new BookCommentDto(null, text, idBook);
    }
}
