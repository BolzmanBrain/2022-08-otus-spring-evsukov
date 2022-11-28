package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UniqueKeyViolatedException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthorDaoH2")
@JdbcTest
@Import(value = {AuthorDaoH2.class, BookDaoH2.class, GenreDaoH2.class})
class AuthorDaoH2Test {
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private BookDao bookDao;

    @DisplayName("getById() works correctly if an author exists")
    @Test
    void shouldGetByIdCorrectlyIfExists() {
        final int EXPECTED_ID = 1;
        final String EXPECTED_NAME = "Jack London";

        Author author = authorDao.getById(EXPECTED_ID).orElseThrow();

        assertAll(
                () -> assertEquals(EXPECTED_ID, author.getIdAuthor()),
                () -> assertEquals(EXPECTED_NAME, author.getName()));
    }

    @DisplayName("getById() throws exception if an author doesn't exist")
    @Test
    void shouldGetByIdCorrectlyIfDoesntExist() {
        Optional<Author> optAuthor = authorDao.getById(-1);
        assertTrue(optAuthor.isEmpty());
    }

    @DisplayName("getAll() works correctly")
    @Test
    void shouldGetAllCorrectly() {
        final int EXPECTED_SIZE = 2;
        final Author EXPECTED_AUTHOR1 = new Author(1,"Jack London");
        final Author EXPECTED_AUTHOR2 = new Author(2,"Ray Bradbury");

        var actual = authorDao.getAll();

        assertAll(
                () -> assertEquals(EXPECTED_SIZE, actual.size()),
                () -> assertEquals(EXPECTED_AUTHOR1, actual.get(0)),
                () -> assertEquals(EXPECTED_AUTHOR2, actual.get(1)));
    }

    @DisplayName("count() works correctly")
    @Test
    void shouldCountCorrectly() {
        final int EXPECTED_COUNT = 2;
        assertEquals(EXPECTED_COUNT, authorDao.count());
    }

    @DisplayName("insert() works correctly")
    @Test
    void shouldInsertCorrectly() throws Exception {
        final String EXPECTED_NAME = "New Author";
        final int EXPECTED_COUNT = authorDao.count() + 1;

        Author authorToInsert = Author.createWithNullId(EXPECTED_NAME);

        int newId = authorDao.insert(authorToInsert);
        int actualCount = authorDao.count();
        Author insertedAuthor = authorDao.getById(newId).orElseThrow();

        assertAll(
                () -> assertEquals(EXPECTED_NAME, insertedAuthor.getName()),
                () -> assertEquals(EXPECTED_COUNT, actualCount));
    }

    @DisplayName("insert() throws UniqueKeyViolatedException")
    @Test
    void shouldInsertThrowUniqueKeyViolatedException() {
        final int ID_AUTHOR_TO_SELECT = 1;
        Author existingAuthor = authorDao.getById(ID_AUTHOR_TO_SELECT).orElseThrow();

        Author authorToInsert = Author.createWithNullId(existingAuthor.getName());
        assertThrows(UniqueKeyViolatedException.class,
                () -> authorDao.insert(authorToInsert));
    }

    @DisplayName("update() throws UniqueKeyViolatedException")
    @Test
    void shouldThrowUniqueKeyViolatedExceptionUpdateCorrectly() {
        final int ID_TO_UPDATE = 1;
        final int ID_TO_SELECT_NAME = 2;

        Author existingAuthor = authorDao.getById(ID_TO_SELECT_NAME).orElseThrow();
        Author authorToUpdate = new Author(ID_TO_UPDATE,existingAuthor.getName());

        assertThrows(UniqueKeyViolatedException.class,
                () -> authorDao.update(authorToUpdate));
    }

    @DisplayName("update() works correctly")
    @Test
    void shouldUpdateCorrectly() throws Exception {
        final int ID_TO_UPDATE = 2;
        final String EXPECTED_NAME = "Jack Paris";
        Author updatedAuthor = new Author(ID_TO_UPDATE,EXPECTED_NAME);

        authorDao.update(updatedAuthor);

        Author actualAuthor = authorDao.getById(ID_TO_UPDATE).orElseThrow();

        assertEquals(EXPECTED_NAME, actualAuthor.getName());
    }

    @DisplayName("deleteById() works correctly")
    @Test
    void shouldDeleteByIdCorrectly() throws Exception {
        deleteAllBooks();
        final int ID_TO_DELETE = 1;

        // check if the author exists before deleting
        Author author = authorDao.getById(ID_TO_DELETE).orElseThrow();

        authorDao.deleteById(ID_TO_DELETE);

        assertAll(
                () -> assertNotNull(author),
                () -> assertTrue(authorDao.getById(ID_TO_DELETE).isEmpty()));
    }

    @DisplayName("deleteById() throws ForeignKeyViolatedException")
    @Test
    void shouldDeleteThrowForeignKeyViolatedException() {
        final int ID_TO_DELETE = 1;

        assertThrows(ForeignKeyViolatedException.class,
                () -> authorDao.deleteById(ID_TO_DELETE));
    }

    private void deleteAllBooks() {
        List<Book> books = bookDao.getAll();

        for(Book book : books) {
            bookDao.deleteById(book.getIdBook());
        }
    }
}