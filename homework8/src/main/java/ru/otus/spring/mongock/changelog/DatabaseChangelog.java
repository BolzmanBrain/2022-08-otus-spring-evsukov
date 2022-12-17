package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "evsukov_mv", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    // Using MongoDB classes to manipulate data
    @ChangeSet(order = "002", id = "insertAuthors", author = "evsukov_mv")
    public void insertAuthors(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("authors");
        List<Document> authorDocs = List.of(
                new Document().append("name", "Jack London"),
                new Document().append("name", "Ray Bradbury")
        );
        myCollection.insertMany(authorDocs);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "evsukov_mv")
    public void insertGenres(GenreRepository repository) {
        repository.save(Genre.createForInsert("Novel"));
        repository.save(Genre.createForInsert("Science fiction"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "evsukov_mv")
    public void insertBooks(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        var authors = authorRepository.findAll();
        var genres = genreRepository.findAll();

        var bookComments = List.of(
            BookComment.createForInsert("Захватывающе"),
                BookComment.createForInsert("Меня это потрясло до глубины души!"),
                BookComment.createForInsert("Убийца - садовник"),
                BookComment.createForInsert("We need MOAR lesbian cosplayers")
        );
        var book0 = Book.createForInsert("The Call of the Wild",
                authors.get(0),genres.get(0),bookComments.subList(0,2));
        var book1 = Book.createForInsert("Martian Chronicles",
                authors.get(1),genres.get(1),bookComments.subList(2,4));

        bookRepository.save(book0);
        bookRepository.save(book1);
    }
}
