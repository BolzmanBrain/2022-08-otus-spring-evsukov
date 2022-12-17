package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class BookComment {
    // Auto generate with new ObjectId().toString()
    @Id
    private String id = new ObjectId().toString();
    private String text;

    public BookComment() {
        this.text = null;
    }

    public static BookComment createForInsert(String text) {
        return new BookComment(text);
    }

    private BookComment(String text) {
        this.text = text;
    }
}
