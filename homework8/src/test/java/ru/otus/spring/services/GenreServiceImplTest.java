package ru.otus.spring.services;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exceptions.ConsistencyViolatedException;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.services.GenreServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GenreServiceImpl")
@DataMongoTest
@Import(GenreServiceImpl.class)
class GenreServiceImplTest {
    @Autowired
    private GenreServiceImpl service;
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("getById works correctly for existing record")
    @Test
    void getById_ExistingRecord() {
        String ID = getExistingId();
        String EXPECTED_NAME = "TestGenre";

        val actual = service.findById(ID).orElseThrow();
        assertEquals(EXPECTED_NAME,actual.getName());
    }

    @DisplayName("getById returns empty optional for absent record")
    @Test
    void getById_AbsentRecord_EmptyOptional() {
        String ID_TO_SELECT = "id";

        val actual = service.findById(ID_TO_SELECT);
        assertThat(actual).isEmpty();
    }

    @DisplayName("getAll returns list of 1 element with non-null fields")
    @Test
    void getAll_ListOf1ElemWithNonNullFields() {
        val EXPECTED_SIZE = 1;

        val actual = service.findAll();
        assertAll(
                () -> assertEquals(EXPECTED_SIZE, actual.size()),
                () -> assertNotNull(actual.get(0).getId()),
                () -> assertNotNull(actual.get(0).getName())
        );
    }

    @DisplayName("count returns 1")
    @Test
    void count_Returns1() {
        val EXPECTED_COUNT = 1;
        val actual = service.count();
        assertEquals(EXPECTED_COUNT, actual);
    }

    @DisplayName("save inserts a new record")
    @Test
    void save_insertsANewRecord() {
        val objToInsert = Genre.createForInsert("New record");
        val expectedCount = service.count() + 1;

        val resultObj = service.save(objToInsert);
        val newCount = service.count();

        assertAll(
                () -> assertEquals(expectedCount, newCount),
                () -> assertEquals(objToInsert, resultObj)
        );
    }

    // This annotation is used to restore data state after modification
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("save updates an existing record")
    @Test
    void save_updatesExistingRecord() {
        String ID = getExistingId();
        val updatedObj = new Genre(ID, "New Name");

        service.save(updatedObj);
        val newObj = service.findById(ID).orElseThrow();

        assertEquals(newObj, updatedObj);
    }

    // This annotation is used to restore data state after modification
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("deleteById deletes an existing record")
    @Test
    void deleteById_deletesExistingRecord() {
        deleteAllBooks();
        String ID = getExistingId();
        val recordBefore = service.findById(ID).orElseThrow();

        // delete the object
        service.deleteById(recordBefore.getId());

        val optionalRecordAfter = service.findById(ID);

        assertAll(
                () -> assertNotNull(recordBefore),
                () -> assertThat(optionalRecordAfter).isEmpty()
        );
    }

    @DisplayName("delete throws exception")
    @Test
    void deleteById_throwsException() {
        String ID = getExistingId();
        val recordBefore = service.findById(ID).orElseThrow();
        assertThrows(ConsistencyViolatedException.class, () -> service.deleteById(recordBefore.getId()));
    }

    private String getExistingId() {
        return service.findAll().get(0).getId();
    }

    private void deleteAllBooks() {
        bookRepository.deleteAll();
    }
}