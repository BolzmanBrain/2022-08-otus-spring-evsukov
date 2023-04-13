package ru.otus.spring.dtos;

import lombok.Data;
import ru.otus.spring.domain.Book;

@Data
public class BookDto {
    private final String id;
    private final String name;
    private final String idAuthor;

    public static BookDto createFromDomainObject(Book book) {
        return new BookDto(book.getId(), book.getName(), book.getAuthor().getId());
    }
}
