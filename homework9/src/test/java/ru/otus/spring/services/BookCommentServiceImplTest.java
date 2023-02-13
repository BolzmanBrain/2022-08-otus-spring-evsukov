package ru.otus.spring.services;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.dtos.BookCommentDto;
import ru.otus.spring.services.BookCommentServiceImpl;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookCommentServiceImpl")
@DataJpaTest
@Import(BookCommentServiceImpl.class)
class BookCommentServiceImplTest {
    @Autowired
    private TestEntityManager tem;
    @Autowired
    private BookCommentServiceImpl service;

    @DisplayName("getById works correctly for existing record")
    @Test
    void getById_ExistingRecord() {
        long ID_TO_SELECT = 1;

        val expected = tem.find(BookComment.class, ID_TO_SELECT);
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

    @DisplayName("count returns 4")
    @Test
    void count_Returns4() {
        val EXPECTED_COUNT = 4;
        val actual = service.count();
        assertEquals(EXPECTED_COUNT, actual);
    }

    @DisplayName("save inserts a new record")
    @Test
    void save_insertsANewRecord() {
        EntityManager em = tem.getEntityManager();

        long ID_BOOK = 1;
        String BOOK_COMMENT_TEXT = "Comment text";

        //val book = em.find(Book.class, ID_BOOK);
        val newBookCommentDto = BookCommentDto.createForInsert(BOOK_COMMENT_TEXT, ID_BOOK);

        service.save(newBookCommentDto);
        val actualBookComment = service.findById(5).orElseThrow();

        assertAll(
                () -> assertEquals(newBookCommentDto.getIdBook(), actualBookComment.getBook().getId()),
                () -> assertEquals(newBookCommentDto.getText(), actualBookComment.getText())
        );
    }

    @DisplayName("save updates an existing record")
    @Test
    void save_updatesExistingRecord() {
        long ID_TO_UPDATE = 1;

        val oldObj = service.findById(ID_TO_UPDATE).orElseThrow();
        var updatedObj = BookCommentDto.createForUpdate(ID_TO_UPDATE, "New Name");

        service.save(updatedObj);

        val newObj = service.findById(ID_TO_UPDATE).orElseThrow();

        assertAll(
                () -> assertEquals(oldObj.getId(), newObj.getId()),
                () -> assertEquals(updatedObj.getText(), newObj.getText()));
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

}