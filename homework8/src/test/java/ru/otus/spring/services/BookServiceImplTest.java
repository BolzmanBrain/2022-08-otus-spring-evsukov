package ru.otus.spring.services;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dtos.BookDto;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.services.BookServiceImpl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookServiceImpl")
@DataMongoTest
@Import(BookServiceImpl.class)
class BookServiceImplTest {
    @Autowired
    private BookServiceImpl service;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("getById works correctly for existing record")
    @Test
    void getById_ExistingRecord() {
        String ID = getExistingId();
        String EXPECTED_NAME = "TestBook";

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
    void count_Returns2() {
        val EXPECTED_COUNT = 1;
        val actual = service.count();
        assertEquals(EXPECTED_COUNT, actual);
    }

    // This annotation is used to restore data state after modification
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("save inserts a new record")
    @Test
    void save_insertsANewRecord() {
        val author = getAuthor();
        val genre = getGenre();

        val objToInsert = BookDto.createForInsert("New record", author.getId(), genre.getId());
        val expectedCount = service.count() + 1;

        service.save(objToInsert);
        val newCount = service.count();

        assertEquals(expectedCount, newCount);
    }

    // This annotation is used to restore data state after modification
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("save updates an existing record")
    @Test
    void save_updatesExistingRecord() {
        String id = getExistingId();

        val oldObj = service.findById(id).orElseThrow();
        var updatedObj = new BookDto(id, "New Name", oldObj.getAuthor().getId(),
                oldObj.getGenre().getId());

        service.save(updatedObj);

        val newObj = service.findById(id).orElseThrow();

        assertAll(
                () -> assertEquals(oldObj.getId(), newObj.getId()),
                () -> assertEquals(updatedObj.getName(), newObj.getName()),
                () -> assertEquals(updatedObj.getIdAuthor(), newObj.getAuthor().getId()),
                () -> assertEquals(updatedObj.getIdGenre(), newObj.getGenre().getId())
        );
    }

    // This annotation is used to restore data state after modification
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("deleteById deletes an existing record")
    @Test
    void deleteById_deletesExistingRecord() {
        String id = getExistingId();
        val recordBefore = service.findById(id).orElseThrow();

        // delete the object
        service.deleteById(recordBefore.getId());

        val optionalRecordAfter = service.findById(id);

        assertAll(
                () -> assertNotNull(recordBefore),
                () -> assertThat(optionalRecordAfter).isEmpty()
        );
    }

    private String getExistingId() {
        return service.findAll().get(0).getId();
    }

    private Author getAuthor() {
        return authorRepository.findAll().get(0);
    }

    private Genre getGenre() {
        return genreRepository.findAll().get(0);
    }
}