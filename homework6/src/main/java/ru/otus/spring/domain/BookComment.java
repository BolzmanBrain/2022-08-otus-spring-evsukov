package ru.otus.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "book_comments_tbl")
@Data
@RequiredArgsConstructor
public class BookComment implements RepresentableAsString {
    @Id
    @Column(name = "id_book_comment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "text", nullable = false, length = 8000)
    private final String text;

    @ManyToOne
    @JoinColumn(name = "id_book")
    private final Book book;

    public BookComment() {
        this.id = null;
        this.text = null;
        this.book = null;
    }

    public static BookComment of(String text, Book book) {
        return new BookComment(null, text, book);
    }

    @Override
    public String convertToString() {
        return String.format("BookComment(id = %d, text = %s, idBook = %d)",
                id, text, book == null ? null : book.getId());
    }
}
