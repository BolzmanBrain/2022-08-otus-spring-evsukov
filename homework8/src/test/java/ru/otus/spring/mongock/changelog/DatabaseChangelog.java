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
        myCollection.insertOne(new Document().append("name", "TestAuthor"));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "evsukov_mv")
    public void insertGenres(GenreRepository repository) {
        repository.save(Genre.createForInsert("TestGenre"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "evsukov_mv")
    public void insertBooks(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        var author = authorRepository.findAll().get(0);
        var genre = genreRepository.findAll().get(0);

        var bookComments = List.of(
            BookComment.createForInsert("TestComment1"),
                BookComment.createForInsert("TestComment2")
        );
        var book = Book.createForInsert("TestBook", author,genre,bookComments);
        bookRepository.save(book);
    }
}
