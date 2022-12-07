package ru.otus.spring.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// @NamedEntityGraph is used to solve the N + 1 problem for authors.
// It can be specified with Spring-data-created methods.
// It's a good idea since Spring Data, probably, ignores the FetchMode.
// A tried FETCH_MODE.JOIN, but it didn't work:
// https://syntaxfix.com/question/5870/how-does-the-fetchmode-work-in-spring-data-jpa
@NamedEntityGraph(name = Book.BOOK_ENTITY_GRAPH,
        attributeNodes = {@NamedAttributeNode("author"),@NamedAttributeNode("genre")})
@Entity
@Table(name = "books_tbl")
@Data
public class Book  {
    public final static String BOOK_ENTITY_GRAPH = "book-entity-graph";
    @Id
    @Column(name = "id_book")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "name")
    private final String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_author")
    private final Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_genre")
    private final Genre genre;

    // If I don't create the collection right here, Hibernate throws Exception while trying
    // to update this entity. Even if the 'name' field is being updated
    // https://betterjavacode.com/java/a-collection-with-cascadeall-delete-orphan-was-no-longer-referenced-by-the-owning-entity-instance
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "book",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private final List<BookComment> comments = new ArrayList<>();

    public Book() {
        this.id = null;
        this.name = null;
        this.author = new Author();
        this.genre = new Genre();
    }

    public Book(Long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public static Book createForInsert(String name, Author author, Genre genre) {
        return new Book(null, name, author, genre);
    }

    @Override
    public String toString() {
        return String.format("Book(id = %d, name = %s, author = %s, genre = %s)",
                id, name, author.toString(), genre.toString());
    }
}
