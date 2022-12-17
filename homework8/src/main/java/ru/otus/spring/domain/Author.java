package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("authors")
@Data
@AllArgsConstructor
public class Author {
    @Id
    private String id;
    private String name;

    public Author() {
        this.id = null;
        this.name = null;
    }

    public static Author createForInsert(String name) {
        return new Author(null,name);
    }

}
