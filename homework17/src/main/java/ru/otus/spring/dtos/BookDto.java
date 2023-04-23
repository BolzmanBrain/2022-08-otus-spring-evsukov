package ru.otus.spring.dtos;

import lombok.Data;
import org.springframework.lang.Nullable;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@Data
public class BookDto {
    @Nullable
    private final String id;
    private final String name;
    private final String idAuthor;
    private final String idGenre;

    public static BookDto createForInsert(String name, String idAuthor, String idGenre) {
        return new BookDto(null, name, idAuthor, idGenre);
    }

    public static BookDto createForUpdate(String id, String name, String idAuthor, String idGenre) {
        return new BookDto(id, name, idAuthor, idGenre);
    }

    public static BookDto toDto(Book book) {
        Author author = book.getAuthor();
        Genre genre = book.getGenre();

        return new BookDto(book.getId(), book.getName(), author.getId(), genre.getId());
    }
}
