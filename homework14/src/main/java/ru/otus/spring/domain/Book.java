package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String id;
    private String name;
    private Author author;

    public static Book createForInsert(String name, Author author) {
        return new Book(null, name, author);
    }

    @Override
    public String toString() {
        return String.format("Book(id = %s, name = %s, author = %s)",
                id, name, author.toString());
    }
}
