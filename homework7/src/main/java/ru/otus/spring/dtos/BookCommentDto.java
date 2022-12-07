package ru.otus.spring.dtos;

import lombok.Data;

@Data
public class BookCommentDto {
    private final Long id;
    private final String text;
    private final Long idBook;

    public static BookCommentDto createForInsert(String text, Long idBook) {
        return new BookCommentDto(null, text, idBook);
    }

    public static BookCommentDto createForUpdate(Long id, String text) {
        return new BookCommentDto(id, text, null);
    }
}
