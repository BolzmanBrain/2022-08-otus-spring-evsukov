package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.dao.dto.BookDto;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.ForeignKeyViolatedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookDaoH2")
@JdbcTest
@Import(value = {BookDaoH2.class, GenreDaoH2.class, AuthorDaoH2.class})
class BookDaoH2Test {
    @Autowired
    BookDao dao;

    @DisplayName("getById() works correctly if a book exists")
    @Test
    void shouldGetByIdCorrectlyIfExists() {
        final int EXPECTED_ID = 1;
        final String EXPECTED_NAME = "The Call of the Wild";

        Book book = dao.getById(EXPECTED_ID).orElseThrow();

        assertAll(
                () -> assertEquals(EXPECTED_ID, book.getIdBook()),
                () -> assertEquals(EXPECTED_NAME, book.getName()));
    }

    @DisplayName("getById() throws exception if an author doesn't exist")
    @Test
    void shouldGetByIdCorrectlyIfDoesntExist() {
        Optional<Book> optBook = dao.getById(-1);
        assertTrue(optBook.isEmpty());
    }

    @DisplayName("getAll() works correctly")
    @Test
    void shouldGetAllCorrectly() {
        final var EXPECTED_SIZE = 2;
        final int EXPECTED_ID_BOOK1 = 1;
        final int EXPECTED_ID_BOOK2 = 2;

        var actual = dao.getAll();

        assertAll(
                () -> assertEquals(EXPECTED_SIZE, actual.size()),
                () -> assertEquals(EXPECTED_ID_BOOK1, actual.get(0).getIdBook()),
                () -> assertEquals(EXPECTED_ID_BOOK2, actual.get(1).getIdBook()));
    }

    @DisplayName("count() works correctly")
    @Test
    void shouldCountCorrectly() {
        final int EXPECTED_COUNT = 2;
        assertEquals(EXPECTED_COUNT, dao.count());
    }

    @DisplayName("insert() works correctly")
    @Test
    void shouldInsertCorrectly() throws Exception {
        final String EXPECTED_NAME = "New Book";
        final int EXPECTED_COUNT = dao.count() + 1;

        final int ID_AUTHOR = 1;
        final int ID_GENRE = 1;
        BookDto bookDto = BookDto.createWithNullId(EXPECTED_NAME, ID_AUTHOR, ID_GENRE);

        int newId = dao.insert(bookDto);
        int actualCount = dao.count();
        Book insertedBook = dao.getById(newId).orElseThrow();

        assertAll(
                () -> assertEquals(EXPECTED_NAME, insertedBook.getName()),
                () -> assertEquals(EXPECTED_COUNT, actualCount));
    }

    @DisplayName("insert() throws ForeignKeyViolatedException")
    @Test
    void shouldInsertThrowForeignKeyViolatedException() {
        final String NAME = "New Book";
        final int ID_AUTHOR = 3;
        final int ID_GENRE = 3;
        BookDto bookDto = BookDto.createWithNullId(NAME, ID_AUTHOR, ID_GENRE);

        assertThrows(ForeignKeyViolatedException.class,
                () -> dao.insert(bookDto));
    }

    @DisplayName("update() works correctly")
    @Test
    void shouldUpdateCorrectly() throws Exception {
        final int ID_BOOK_TO_UPDATE = 1;
        final String EXPECTED_NAME = "Hello World";

        final int ID_AUTHOR = 2;
        final int ID_GENRE = 2;
        BookDto bookDtoToUpdate = new BookDto(ID_BOOK_TO_UPDATE, EXPECTED_NAME, ID_AUTHOR, ID_GENRE);

        dao.update(bookDtoToUpdate);

        Book actualBook = dao.getById(ID_BOOK_TO_UPDATE).orElseThrow();

        assertAll(
                () -> assertEquals(EXPECTED_NAME, actualBook.getName()),
                () -> assertEquals(ID_AUTHOR, actualBook.getAuthor().getIdAuthor()),
                () -> assertEquals(ID_GENRE, actualBook.getGenre().getIdGenre()));
    }

    @DisplayName("update() throws ForeignKeyViolatedException")
    @Test
    void shouldUpdateThrowForeignKeyViolatedException() {
        final int ID_BOOK_TO_UPDATE = 1;
        final String EXPECTED_NAME = "Hello World";
        final int ID_AUTHOR = 3;
        final int ID_GENRE = 3;
        BookDto bookDtoToUpdate = new BookDto(ID_BOOK_TO_UPDATE, EXPECTED_NAME, ID_AUTHOR, ID_GENRE);

        assertThrows(ForeignKeyViolatedException.class,
                () -> dao.update(bookDtoToUpdate));
    }

    @DisplayName("deleteById() works correctly")
    @Test
    void shouldDeleteByIdCorrectly() {
        final int ID_TO_DELETE = 1;

        // check if the book exists before deleting
        Book book = dao.getById(ID_TO_DELETE).orElseThrow();

        dao.deleteById(ID_TO_DELETE);

        assertAll(
                () -> assertNotNull(book),
                () -> assertTrue(dao.getById(ID_TO_DELETE).isEmpty()));
    }
}