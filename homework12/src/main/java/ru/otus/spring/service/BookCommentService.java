package ru.otus.spring.service;

import ru.otus.spring.domain.BookComment;
import ru.otus.spring.dto.BookCommentDto;

import java.util.Optional;

public interface BookCommentService {
    Optional<BookComment> findById(long id);
    long count();
    BookComment save(BookCommentDto bookCommentDto);
    void deleteById(long id);
}
