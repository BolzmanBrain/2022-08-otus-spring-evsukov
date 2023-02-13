package ru.otus.spring.services;

import ru.otus.spring.domain.BookComment;
import ru.otus.spring.dtos.BookCommentDto;

import java.util.Optional;

public interface BookCommentService {
    Optional<BookComment> findByIds(String idBook, String idBookComment);
    BookComment save(BookCommentDto bookCommentDto);
    void deleteByIds(String idBook, String idBookComment);
}
