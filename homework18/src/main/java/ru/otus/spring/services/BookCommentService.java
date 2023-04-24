package ru.otus.spring.services;

import ru.otus.spring.domain.BookComment;
import ru.otus.spring.dtos.BookCommentDto;

import java.util.Optional;

public interface BookCommentService {
    Optional<BookComment> findById(long id);
    long count();
    BookComment save(BookCommentDto bookCommentDto);
    void deleteById(long id);
}
