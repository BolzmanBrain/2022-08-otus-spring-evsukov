package ru.otus.spring.repository;

import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {
    Optional<BookComment> getById(long id);
    List<BookComment> getByBookId(long idBook);
    long count();
    BookComment save(BookComment bookComment);
    void deleteById(long id);

}
