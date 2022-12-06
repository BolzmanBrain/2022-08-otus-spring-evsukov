package ru.otus.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookRepositoryJpa")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {
    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager tem;

    @DisplayName("getById works correctly for existing record")
    @Test
    void getById_ExistingRecord() {
        long ID_TO_SELECT = 1;

        val expected = tem.find(Book.class, ID_TO_SELECT);
        val actual = repositoryJpa.findById(ID_TO_SELECT).orElseThrow();
        assertEquals(expected, actual);
    }

    @DisplayName("getById returns empty optional for absent record")
    @Test
    void getById_AbsentRecord_EmptyOptional() {
        long ID_TO_SELECT = -1;
        val actual = repositoryJpa.findById(ID_TO_SELECT);
        assertThat(actual).isEmpty();
    }

    @DisplayName("getAll returns list of 2 elements with non-null fields")
    @Test
    void getAll_ListOf2ElemsWithNonNullFields() {
        val EXPECTED_SIZE = 2;

        val actual = repositoryJpa.findAll();

        assertThat(actual).hasSize(EXPECTED_SIZE);

        assertAll(
                () -> assertEquals(EXPECTED_SIZE, actual.size()),
                () -> assertNotNull(actual.get(0).getId()),
                () -> assertNotNull(actual.get(1).getName()),
                () -> assertNotNull(actual.get(0).getId()),
                () -> assertNotNull(actual.get(1).getName())
        );
    }

    @DisplayName("count returns 2")
    @Test
    void count_Returns2() {
        val EXPECTED_COUNT = 2;
        val actual = repositoryJpa.count();
        assertEquals(EXPECTED_COUNT, actual);
    }

    @DisplayName("save inserts a new record")
    @Test
    void save_insertsANewRecord() {
        long idAuthor = 1;
        long idGenre = 1;
        val em = tem.getEntityManager();
        val author = em.find(Author.class, idAuthor);
        val genre = em.find(Genre.class, idGenre);

        val objToInsert = Book.of("New record", author, genre);
        val expectedCount = repositoryJpa.count() + 1;

        repositoryJpa.save(objToInsert);
        val newCount = repositoryJpa.count();

        val newObj = repositoryJpa.findById(3).orElseThrow();

        assertAll(
                () -> assertEquals(expectedCount, newCount),
                () -> assertEquals(objToInsert.getName(), newObj.getName()),
                () -> assertEquals(objToInsert.getAuthor(), newObj.getAuthor()),
                () -> assertEquals(objToInsert.getGenre(), newObj.getGenre())
        );
    }

    @DisplayName("save updates an existing record")
    @Test
    void save_updatesExistingRecord() {
        long ID_TO_UPDATE = 1;

        val oldObj = repositoryJpa.findById(ID_TO_UPDATE).orElseThrow();
        var updatedObj = new Book(ID_TO_UPDATE, "New Name", oldObj.getAuthor(),
                oldObj.getGenre());

        repositoryJpa.save(updatedObj);

        val newObj = repositoryJpa.findById(ID_TO_UPDATE).orElseThrow();

        assertAll(
                () -> assertEquals(oldObj.getId(), newObj.getId()),
                () -> assertEquals(updatedObj.getName(), newObj.getName()),
                () -> assertEquals(updatedObj.getAuthor(), newObj.getAuthor()),
                () -> assertEquals(updatedObj.getGenre(), newObj.getGenre())
        );
    }

    @DisplayName("deleteById deletes an existing record")
    @Test
    void deleteById_deletesExistingRecord() {
        long ID_TO_DELETE = 1;
        val recordBefore = repositoryJpa.findById(ID_TO_DELETE).orElseThrow();

        // delete the object
        repositoryJpa.delete(recordBefore);

        val optionalRecordAfter = repositoryJpa.findById(ID_TO_DELETE);

        assertAll(
                () -> assertNotNull(recordBefore),
                () -> assertThat(optionalRecordAfter).isEmpty()
        );
    }
}