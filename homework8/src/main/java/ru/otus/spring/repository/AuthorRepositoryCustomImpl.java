package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {
    private final MongoTemplate mongoTemplate;
    private final BookRepository bookRepository;

    @Override
    public void deleteEnsureConsistencyById(String id) {
        if (bookRepository.existsByAuthorId(id)) {
            throw new RuntimeException("The author can't be deleted, because there are books which contain it.");
        }
        Query query = Query.query(Criteria.where("_id").is(new ObjectId(id)));
        mongoTemplate.remove(query, Author.class);
    }

    @Override
    public Author saveEnsureConsistency(Author author) {
        if (author.getId() == null) {
            return insert(author);
        }
        return update(author);
    }

    private Author insert(Author author) {
        throwExceptionIfExistsAuthorWithSameName(author);
        return mongoTemplate.save(author);
    }

    private Author update(Author author) {
        throwExceptionIfExistsAuthorWithSameName(author);
        updateAuthorsEmbeddedInBooks(author);
        return mongoTemplate.save(author);
    }

    private void throwExceptionIfExistsAuthorWithSameName(Author author) {
        val query = Query.query(Criteria.where("name").is(author.getName()));

        if (mongoTemplate.exists(query, Author.class)) {
            throw new RuntimeException("An author with specified name already exists");
        }
    }

    private void updateAuthorsEmbeddedInBooks(Author author) {
        val query = Query.query(Criteria.where("author._id").is(new ObjectId(author.getId())));
        val update = new Update().set("author.name", author.getName());
        mongoTemplate.updateMulti(query, update, Book.class);
    }
}
