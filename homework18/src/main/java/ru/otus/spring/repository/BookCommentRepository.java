package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.BookComment;

public interface BookCommentRepository extends JpaRepository<BookComment,Long> {
}
