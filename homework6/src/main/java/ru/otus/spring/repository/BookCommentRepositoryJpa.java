package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<BookComment> getById(long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }

    @Override
    public List<BookComment> getByBookId(long idBook) {
        TypedQuery<BookComment> query = em.createQuery("select bc from BookComment bc " +
                "where bc.book.id = :id " +
                "order by bc.id", BookComment.class);
        query.setParameter("id", idBook);
        return query.getResultList();
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
    public void deleteById(long id) {
        Optional<BookComment> optionalBookComment = getById(id);

        if (optionalBookComment.isPresent()) {
            BookComment bookComment = optionalBookComment.get();
            em.remove(bookComment);
        }
    }
}
