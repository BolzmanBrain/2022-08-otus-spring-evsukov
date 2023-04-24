package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("genres")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    private String id;
    private String name;

    public static Genre createForInsert(String name) {
        return new Genre(null,name);
    }
}
