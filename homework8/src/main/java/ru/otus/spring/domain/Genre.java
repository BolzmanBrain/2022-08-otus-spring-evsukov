package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("genres")
@Data
@AllArgsConstructor
public class Genre {
    @Id
    private String id;
    private String name;

    public Genre() {
        this.id = null;
        this.name = null;
    }

    public static Genre createForInsert(String name) {
        return new Genre(null,name);
    }
}
