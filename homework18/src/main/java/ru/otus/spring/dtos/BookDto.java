package ru.otus.spring.dtos;

import lombok.Data;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@Data
public class BookDto {
    private final Long id;
    private final String name;
    private final Long idAuthor;
    private final Long idGenre;

    private final String authorName;
    private final String genreName;

    public static BookDto createForInsert(String name, Long idAuthor, Long idGenre) {
        return new BookDto(null, name, idAuthor, idGenre, null, null);
    }

    public static BookDto createForUpdate(Long id, String name, Long idAuthor, Long idGenre) {
        return new BookDto(id, name, idAuthor, idGenre, null, null);
    }

    public static BookDto toDto(Book book) {
        Author author = book.getAuthor();
        Genre genre = book.getGenre();

        return new BookDto(book.getId(), book.getName(), author.getId(), genre.getId(), author.getName(), genre.getName());
    }
}
