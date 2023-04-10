package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;

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

    @ChangeSet(order = "003", id = "insertBooks", author = "evsukov_mv")
    public void insertBooks(BookRepository bookRepository, AuthorRepository authorRepository) {
        var authors = authorRepository.findAll();

        var book0 = Book.createForInsert("The Call of the Wild", authors.get(0));
        var book1 = Book.createForInsert("Martian Chronicles", authors.get(1));

        bookRepository.save(book0);
        bookRepository.save(book1);
    }
}
