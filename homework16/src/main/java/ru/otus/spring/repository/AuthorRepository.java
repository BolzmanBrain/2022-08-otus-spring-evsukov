package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.domain.Author;

@RepositoryRestResource(path = "author")
public interface AuthorRepository extends JpaRepository<Author,Long> {
}
