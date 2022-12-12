package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    // transactional is needed for correct retrieval of LAZY-field "comments"
    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
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
    public void delete(Book book) {
        em.remove(book);
    }
}
