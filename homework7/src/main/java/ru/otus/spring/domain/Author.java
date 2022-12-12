package ru.otus.spring.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "authors_tbl")
@Data
@RequiredArgsConstructor
public class Author {
    @Id
    @Column(name = "id_author")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "name", unique = true)
    private final String name;

    public Author() {
        this.id = null;
        this.name = null;
    }

    public static Author createForInsert(String name) {
        return new Author(null,name);
    }

}
