package ru.otus.spring.services;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.services.AuthorServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthorServiceImpl")
@DataJpaTest
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {
    @Autowired
    private AuthorServiceImpl service;
    @Autowired
    private TestEntityManager tem;

    @DisplayName("getById works correctly for existing record")
    @Test
    void getById_ExistingRecord() {
        long ID_TO_SELECT = 1;

        val expected = tem.find(Author.class, ID_TO_SELECT);
        val actual = service.findById(ID_TO_SELECT).orElseThrow();

        assertEquals(expected, actual);
    }

    @DisplayName("getById returns empty optional for absent record")
    @Test
    void getById_AbsentRecord_EmptyOptional() {
        long ID_TO_SELECT = -1;
        val actual = service.findById(ID_TO_SELECT);
        assertThat(actual).isEmpty();
    }

    @DisplayName("getAll returns list of 2 elements with non-null fields")
    @Test
    void getAll_ListOf2ElemsWithNonNullFields() {
        val EXPECTED_SIZE = 2;

        val actual = service.findAll();

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
        val actual = service.count();
        assertEquals(EXPECTED_COUNT, actual);
    }

    @DisplayName("save inserts a new record")
    @Test
    void save_insertsANewRecord() {
        val objToInsert = Author.createForInsert("New record");
        val expectedCount = service.count() + 1;

        val resultObj = service.save(objToInsert);
        val newCount = service.count();

        assertAll(
                () -> assertEquals(expectedCount, newCount),
                () -> assertEquals(objToInsert, resultObj)
        );
    }

    @DisplayName("save updates an existing record")
    @Test
    void save_updatesExistingRecord() {
        long ID_TO_UPDATE = 1;

        final Author updatedObj = new Author(ID_TO_UPDATE, "New Name");

        service.save(updatedObj);
        final Author newObj = service.findById(ID_TO_UPDATE).orElseThrow();

        assertEquals(newObj, updatedObj);
    }

    @DisplayName("delete deletes an existing record")
    @Test
    void deleteById_deletesExistingRecord() {
        long ID_TO_DELETE = 1;
        val recordBefore = service.findById(ID_TO_DELETE).orElseThrow();

        // delete the object
        service.deleteById(recordBefore.getId());

        val optionalRecordAfter = service.findById(ID_TO_DELETE);

        assertAll(
                () -> assertNotNull(recordBefore),
                () -> assertThat(optionalRecordAfter).isEmpty()
        );
    }
}