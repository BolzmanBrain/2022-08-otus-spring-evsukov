package ru.otus.spring.services;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exceptions.dtos.BookDto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookServiceImpl")
@DataJpaTest
@Import(BookServiceImpl.class)
class BookServiceImplTest {
    @Autowired
    private BookServiceImpl service;

    @Autowired
    private TestEntityManager tem;

    @DisplayName("getById works correctly for existing record")
    @Test
    void getById_ExistingRecord() {
        long ID_TO_SELECT = 1;

        val expected = tem.find(Book.class, ID_TO_SELECT);
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
    @Transactional
    void save_insertsANewRecord() {
        long idAuthor = 1;
        long idGenre = 1;

        val objToInsert = BookDto.createForInsert("New record", idAuthor, idGenre);
        val expectedCount = service.count() + 1;

        service.save(objToInsert);
        val newCount = service.count();

        val newObj = selectBookWithMaxId();

        assertAll(
                () -> assertEquals(expectedCount, newCount),
                () -> assertEquals(objToInsert.getName(), newObj.getName()),
                () -> assertEquals(objToInsert.getIdAuthor(), newObj.getAuthor().getId()),
                () -> assertEquals(objToInsert.getIdGenre(), newObj.getGenre().getId())
        );
    }

    @DisplayName("save updates an existing record")
    @Test
    void save_updatesExistingRecord() {
        long ID_TO_UPDATE = 1;

        val oldObj = service.findById(ID_TO_UPDATE).orElseThrow();
        var updatedObj = BookDto.createForUpdate(ID_TO_UPDATE, "New Name", oldObj.getAuthor().getId(),
                oldObj.getGenre().getId());

        service.save(updatedObj);

        val newObj = service.findById(ID_TO_UPDATE).orElseThrow();
        //val newObj = selectWithJpql(ID_TO_UPDATE);

        assertAll(
                () -> assertEquals(oldObj.getId(), newObj.getId()),
                () -> assertEquals(updatedObj.getName(), newObj.getName()),
                () -> assertEquals(updatedObj.getIdAuthor(), newObj.getAuthor().getId()),
                () -> assertEquals(updatedObj.getIdGenre(), newObj.getGenre().getId())
        );
    }

    @DisplayName("deleteById deletes an existing record")
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

    private Book selectBookWithMaxId() {
        EntityManager em = tem.getEntityManager();

        TypedQuery<Book> selectBookQuery = em.createQuery("select b from Book b where b.id = " +
                "(select max(a.id) from Book a)", Book.class);
        return selectBookQuery.getSingleResult();
    }
}