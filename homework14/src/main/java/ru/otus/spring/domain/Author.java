package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    private String id;
    private String name;
}
