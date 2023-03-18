package ru.otus.spring.dto;

import lombok.Data;
import org.springframework.lang.Nullable;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@Data
public class BookDto {
    @Nullable
    private final Long id;
    private final String name;
    private final Long idAuthor;
    private final Long idGenre;

    public static BookDto createForInsert(String name, Long idAuthor, Long idGenre) {
        return new BookDto(null, name, idAuthor, idGenre);
    }

    public static BookDto createForUpdate(Long id, String name, Long idAuthor, Long idGenre) {
        return new BookDto(id, name, idAuthor, idGenre);
    }

    public static BookDto toDto(Book book) {
        Author author = book.getAuthor();
        Genre genre = book.getGenre();

        return new BookDto(book.getId(), book.getName(), author.getId(), genre.getId());
    }
}
