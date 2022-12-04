package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a order by a.id", Author.class);
        return query.getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(*) from Author", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Author save(Author author) {
        if (Objects.isNull(author.getId())) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public void deleteById(long id) {
        Optional<Author> optionalAuthor = getById(id);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            em.remove(author);
        }
    }
}
