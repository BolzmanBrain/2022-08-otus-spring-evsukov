package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        // Using JOIN FETCH to solve N + 1 problem for genres
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                "join fetch b.genre " +
                "order by b.id", Book.class);

        // Using the Entity Graph as hint to solve N + 1 problem for authors
        EntityGraph<?> entityGraph = em.getEntityGraph(Book.BOOK_AUTHOR_ENTITY_GRAPH);
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        return query.getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(*) from Book", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Optional<Book> optionalBook = getById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            em.remove(book);
        }
    }
}
