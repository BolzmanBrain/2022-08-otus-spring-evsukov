package ru.otus.spring.repository;

import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {
    Optional<BookComment> findById(long id);
    long count();
    BookComment save(BookComment bookComment);
    void delete(BookComment bookComment);
}
