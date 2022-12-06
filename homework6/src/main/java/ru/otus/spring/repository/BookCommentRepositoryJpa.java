package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<BookComment> findById(long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }


    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(*) from BookComment", Long.class);
        return query.getSingleResult();
    }

    @Override
    public BookComment save(BookComment bookComment) {
        if (bookComment.getId() == null) {
            em.persist(bookComment);
            return bookComment;
        }
        return em.merge(bookComment);
    }

    @Override
    public void delete(BookComment bookComment) {
        em.remove(bookComment);
    }
}
