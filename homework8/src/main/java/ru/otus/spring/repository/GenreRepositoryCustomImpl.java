package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {
    private final MongoTemplate mongoTemplate;
    private final BookRepository bookRepository;

    @Override
    public void deleteEnsureConsistencyById(String id) {
        if (bookRepository.existsByGenreId(id)) {
            throw new RuntimeException("The genre can't be deleted, because there are books which contain it.");
        }
        Query query = Query.query(Criteria.where("_id").is(new ObjectId(id)));
        mongoTemplate.remove(query, Genre.class);
    }

    @Override
    public Genre saveEnsureConsistency(Genre genre) {
        if (genre.getId() == null) {
            return insert(genre);
        }
        return update(genre);
    }

    private Genre insert(Genre genre) {
        throwExceptionIfExistsGenreWithSameName(genre);
        return mongoTemplate.save(genre);
    }

    private Genre update(Genre genre) {
        throwExceptionIfExistsGenreWithSameName(genre);
        updateGenresEmbeddedInBooks(genre);
        return mongoTemplate.save(genre);
    }

    private void throwExceptionIfExistsGenreWithSameName(Genre genre) {
        val query = Query.query(Criteria.where("name").is(genre.getName()));

        if (mongoTemplate.exists(query, Genre.class)) {
            throw new RuntimeException("A genre with specified name already exists");
        }
    }

    private void updateGenresEmbeddedInBooks(Genre genre) {
        val query = Query.query(Criteria.where("genre._id").is(new ObjectId(genre.getId())));
        val update = new Update().set("genre.name", genre.getName());
        mongoTemplate.updateMulti(query, update, Book.class);
    }
}
