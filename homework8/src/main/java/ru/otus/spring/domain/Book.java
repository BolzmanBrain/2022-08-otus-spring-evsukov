package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book  {
    public final static String BOOK_ENTITY_GRAPH = "book-entity-graph";
    @Id
    private String id;
    private String name;
    private Author author;
    private Genre genre;
    private List<BookComment> comments = new ArrayList<>();

    public Book(String id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public static Book createForInsert(String name, Author author, Genre genre, List<BookComment> bookComments) {
        return new Book(null, name, author, genre, bookComments);
    }

    public static Book createForInsert(String name, Author author, Genre genre) {
        return new Book(null, name, author, genre);
    }

    @Override
    public String toString() {
        return String.format("Book(id = %s, name = %s, author = %s, genre = %s)",
                id, name, author.toString(), genre.toString());
    }
}
