package ru.otus.spring.dtos;

import lombok.Data;
import ru.otus.spring.domain.BookComment;

@Data
public class BookCommentDto {
    private final String id;
    private final String text;
    private final String idBook;

    public static BookCommentDto createForInsert(String text, String idBook) {
        return new BookCommentDto(null, text, idBook);
    }

    public static BookCommentDto createForUpdate(String id, String text, String idBook) {
        return new BookCommentDto(id, text, idBook);
    }

    public static BookCommentDto toDto(BookComment bookComment) {
        return new BookCommentDto(bookComment.getId(), bookComment.getText(), null);
    }
}
