package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    // Solving N + 1 problem
    // https://syntaxfix.com/question/5870/how-does-the-fetchmode-work-in-spring-data-jpa
    @EntityGraph(value = Book.BOOK_ENTITY_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findAll();
}
