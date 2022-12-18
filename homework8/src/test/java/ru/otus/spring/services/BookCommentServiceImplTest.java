package ru.otus.spring.services;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.dtos.BookCommentDto;
import ru.otus.spring.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookCommentServiceImpl")
@DataMongoTest
@Import(BookCommentServiceImpl.class)
class BookCommentServiceImplTest {
    @Autowired
    private BookCommentServiceImpl service;
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("getById works correctly for existing record")
    @Test
    void getById_ExistingRecord() {
        String idBook = getBookId();
        String idComment = getCommentId();
        String EXPECTED_TEXT = "TestComment1";

        val actual = service.findByIds(idBook, idComment).orElseThrow();
        assertEquals(EXPECTED_TEXT,actual.getText());
    }

    @DisplayName("getById returns empty optional for absent record")
    @Test
    void getById_AbsentRecord_EmptyOptional() {
        String idBook = "id";
        String idComment = "id";

        val actual = service.findByIds(idBook, idComment);
        assertThat(actual).isEmpty();
    }

    // This annotation is used to restore data state after modification
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("save inserts a new record")
    @Test
    void save_insertsANewRecord() {
        String idBook = getBookId();

        long numberOfCommentsBefore = getNumberOfCommentsByIdBook(idBook);
        long expectedNumberOfComments = numberOfCommentsBefore + 1;

        val newBookCommentDto = BookCommentDto.createForInsert("TestComment3", idBook);
        service.save(newBookCommentDto);

        long numberOfCommentsAfter = getNumberOfCommentsByIdBook(idBook);
        assertEquals(expectedNumberOfComments, numberOfCommentsAfter);
    }

    // This annotation is used to restore data state after modification
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("save updates an existing record")
    @Test
    void save_updatesExistingRecord() {
        String idBook = getBookId();
        String idComment = getCommentId();
        String NEW_TEXT = "New text";

        val oldObj = service.findByIds(idBook, idComment).orElseThrow();
        var updatedObj = new BookCommentDto(idComment, NEW_TEXT, idBook);

        service.save(updatedObj);

        val newObj = service.findByIds(idBook, idComment).orElseThrow();

        assertAll(
                () -> assertEquals(oldObj.getId(), newObj.getId()),
                () -> assertEquals(updatedObj.getText(), newObj.getText()));
    }

    // This annotation is used to restore data state after modification
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("deleteById deletes an existing record")
    @Test
    void deleteById_deletesExistingRecord() {
        String idBook = getBookId();
        String idComment = getCommentId();
        val recordBefore = service.findByIds(idBook, idComment).orElseThrow();

        // delete the object
        service.deleteByIds(idBook, idComment);

        val optionalRecordAfter = service.findByIds(idBook, idComment);

        assertAll(
                () -> assertNotNull(recordBefore),
                () -> assertThat(optionalRecordAfter).isEmpty()
        );
    }

    private String getBookId() {
        return bookRepository.findAll().get(0).getId();
    }

    private String getCommentId() {
        return bookRepository.findAll().get(0).getComments().get(0).getId();
    }

    private long getNumberOfCommentsByIdBook(String idBook) {
        return bookRepository.findById(idBook).orElseThrow()
                .getComments().size();
    }

}