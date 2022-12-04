package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g order by g.id", Genre.class);
        return query.getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(*) from Genre", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return genre;
        }
        return em.merge(genre);
    }

    @Override
    public void deleteById(long id) {
        Optional<Genre> optionalGenre = getById(id);

        if (optionalGenre.isPresent()) {
            Genre genre = optionalGenre.get();
            em.remove(genre);
        }
    }
}
