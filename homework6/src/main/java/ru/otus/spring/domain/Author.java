package ru.otus.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "authors_tbl")
@Data
@RequiredArgsConstructor
public class Author implements RepresentableAsString{
    @Id
    @Column(name = "id_author")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "name", unique = true, nullable = false)
    private final String name;

    public Author() {
        this.id = null;
        this.name = null;
    }

    public static Author createWithName(String name) {
        return new Author(null,name);
    }
    public static Author createWithId(long idAuthor) {
        return new Author(idAuthor, null);
    }

    @Override
    public String convertToString() {
        return this.toString();
    }
}
