package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;
import ru.otus.spring.exceptions.UniqueKeyViolatedException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GenreDaoH2")
@JdbcTest
@Import(value = {AuthorDaoH2.class, BookDaoH2.class, GenreDaoH2.class})
class GenreDaoH2Test {
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private BookDao bookDao;

    @DisplayName("getById() works correctly if a genre exists")
    @Test
    void shouldGetByIdCorrectlyIfExists() {
        final int EXPECTED_ID = 1;
        final String EXPECTED_NAME = "Novel";

        Genre genre = genreDao.getById(EXPECTED_ID).orElseThrow();

        assertAll(
                () -> assertEquals(EXPECTED_ID, genre.getIdGenre()),
                () -> assertEquals(EXPECTED_NAME, genre.getName()));
    }

    @DisplayName("getById() throws exception if a genre doesn't exist")
    @Test
    void shouldGetByIdCorrectlyIfDoesntExist() {
        Optional<Genre> optGenre = genreDao.getById(-1);
        assertTrue(optGenre.isEmpty());
    }

    @DisplayName("getAll() works correctly")
    @Test
    void shouldGetAllCorrectly() {
        final var EXPECTED_SIZE = 2;
        final var EXPECTED_GENRE1 = new Genre(1,"Novel");
        final var EXPECTED_GENRE2 = new Genre(2,"Science fiction");

        var actual = genreDao.getAll();

        assertAll(
                () -> assertEquals(EXPECTED_SIZE, actual.size()),
                () -> assertEquals(EXPECTED_GENRE1, actual.get(0)),
                () -> assertEquals(EXPECTED_GENRE2, actual.get(1)));
    }

    @DisplayName("count() works correctly")
    @Test
    void shouldCountCorrectly() {
        final int EXPECTED_COUNT = 2;
        assertEquals(EXPECTED_COUNT, genreDao.count());
    }

    @DisplayName("insert() works correctly")
    @Test
    void shouldInsertCorrectly() throws Exception {
        final String EXPECTED_NAME = "New Genre";
        final int EXPECTED_COUNT = genreDao.count() + 1;

        Genre genreToInsert = Genre.createWithNullId(EXPECTED_NAME);

        int newId = genreDao.insert(genreToInsert);
        int actualCount = genreDao.count();
        Genre insertedGenre = genreDao.getById(newId).orElseThrow();

        assertAll(
                () -> assertEquals(EXPECTED_NAME, insertedGenre.getName()),
                () -> assertEquals(EXPECTED_COUNT, actualCount));
    }

    @DisplayName("insert() throws UniqueKeyViolatedException")
    @Test
    void shouldInsertThrowUniqueKeyViolatedException() {
        final int ID_GENRE_TO_SELECT = 1;
        Genre existingGenre = genreDao.getById(ID_GENRE_TO_SELECT).orElseThrow();

        Genre genreToInsert = Genre.createWithNullId(existingGenre.getName());
        assertThrows(UniqueKeyViolatedException.class,
                () -> genreDao.insert(genreToInsert));
    }

    @DisplayName("update() works correctly")
    @Test
    void shouldUpdateCorrectly() throws Exception {
        final int ID_TO_UPDATE = 1;
        final String EXPECTED_NAME = "Jack Paris";
        Genre updatedGenre = new Genre(ID_TO_UPDATE,EXPECTED_NAME);

        genreDao.update(updatedGenre);

        Genre actualGenre = genreDao.getById(ID_TO_UPDATE).orElseThrow();

        assertEquals(EXPECTED_NAME, actualGenre.getName());
    }

    @DisplayName("update() throws UniqueKeyViolatedException")
    @Test
    void shouldThrowUniqueKeyViolatedExceptionUpdateCorrectly() {
        final int ID_TO_UPDATE = 1;
        final int ID_TO_SELECT_NAME = 2;

        Genre existingGenre = genreDao.getById(ID_TO_SELECT_NAME).orElseThrow();
        Genre genreToUpdate = new Genre(ID_TO_UPDATE,existingGenre.getName());

        assertThrows(UniqueKeyViolatedException.class,
                () -> genreDao.update(genreToUpdate));
    }


    @DisplayName("deleteById() works correctly")
    @Test
    void shouldDeleteByIdCorrectly() throws Exception {
        deleteAllBooks();
        final int ID_TO_DELETE = 1;

        // check if the author exists before deleting
        Genre genre = genreDao.getById(ID_TO_DELETE).orElseThrow();

        genreDao.deleteById(ID_TO_DELETE);

        assertAll(
                () -> assertNotNull(genre),
                () -> assertTrue(genreDao.getById(ID_TO_DELETE).isEmpty()));
    }

    @DisplayName("deleteById() throws ForeignKeyViolatedException")
    @Test
    void shouldDeleteThrowForeignKeyViolatedException() {
        final int ID_TO_DELETE = 1;

        assertThrows(ForeignKeyViolatedException.class,
                () -> genreDao.deleteById(ID_TO_DELETE));
    }

    private void deleteAllBooks() {
        List<Book> books = bookDao.getAll();

        for(Book book : books) {
            bookDao.deleteById(book.getIdBook());
        }
    }
}