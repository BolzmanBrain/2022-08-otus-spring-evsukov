package ru.otus.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "genres_tbl")
@Data
@RequiredArgsConstructor
public class Genre {
    @Id
    @Column(name = "id_genre")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "name", unique = true)
    private final String name;

    public Genre() {
        this.id = null;
        this.name = null;
    }

    public static Genre createForInsert(String name) {
        return new Genre(null,name);
    }

}
