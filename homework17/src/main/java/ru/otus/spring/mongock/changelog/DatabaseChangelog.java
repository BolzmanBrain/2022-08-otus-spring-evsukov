package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.otus.spring.domain.BookComment;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "evsukov_mv", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

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
    public void insertGenres(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("genres");
        List<Document> docs = List.of(
                new Document().append("name", "Novel"),
                new Document().append("name", "Science fiction")
        );
        myCollection.insertMany(docs);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "evsukov_mv")
    public void insertBooks(MongoDatabase db) {
        MongoCollection<Document> books = db.getCollection("books"),
                authors = db.getCollection("authors"),
                genres = db.getCollection("genres");

        var authorsDocuments = authors.find();
        var genresDocuments = genres.find();

        var bookComments = List.of(
                new Document().append("text","Захватывающе"),
                new Document().append("text","Меня это потрясло до глубины души!"),
                new Document().append("text","Убийца - садовник"),
                new Document().append("text","We need MOAR lesbian cosplayers")
        );

        books.insertMany(List.of(
                new Document()
                        .append("name", "The Call of the Wild")
                        .append("author", authorsDocuments.first())
                        .append("genre", genresDocuments.first())
                        .append("comments", bookComments.subList(0,2)),
                new Document()
                        .append("name", "Martian Chronicles")
                        .append("author", authorsDocuments.skip(1).first())
                        .append("genre", genresDocuments.skip(1).first())
                        .append("comments", bookComments.subList(2,4))
        ));
    }
}
