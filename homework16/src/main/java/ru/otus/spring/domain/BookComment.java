package ru.otus.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "book_comments_tbl")
@Data
@RequiredArgsConstructor
public class BookComment {
    @Id
    @Column(name = "id_book_comment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "text")
    private final String text;

    @ManyToOne
    @JoinColumn(name = "id_book")
    private final Book book;

    public BookComment() {
        this.id = null;
        this.text = null;
        this.book = null;
    }

    public static BookComment createForInsert(String text, Book book) {
        return new BookComment(null, text, book);
    }

}
