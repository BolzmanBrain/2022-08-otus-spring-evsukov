package ru.otus.spring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.*;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QuestionFactoryImpl")
class QuestionFactoryImplTest {

    @DisplayName("creates TextQuestion correctly")
    @Test
    void shouldCreateTextQuestionCorrectly() {
        final int questionId = 1;
        final String questionText = "text?";
        final String correctAnswerStringRepresentation = "answer";

        QuestionFactory factory = new QuestionFactoryImpl();
        QuestionDto dto = QuestionDto.create(questionId, questionText, QuestionFactoryImpl.TEXT_QUESTION_TYPE_ID, correctAnswerStringRepresentation);

        Question question = factory.createQuestion(dto, null);

        assertAll("question",
                () -> assertEquals(questionText, question.text),
                () -> assertInstanceOf(TextQuestion.class, question));
    }

    @DisplayName("binds answer to TextQuestion correctly")
    @Test
    void shouldBindAnswerToTextQuestionCorrectly() {
        final int questionId = 1;
        final String questionText = "text?";
        final String correctAnswerStringRepresentation = "answer";

        final Answer answer = new TextAnswer(correctAnswerStringRepresentation);

        QuestionFactory factory = new QuestionFactoryImpl();
        QuestionDto dto = QuestionDto.create(questionId, questionText, QuestionFactoryImpl.TEXT_QUESTION_TYPE_ID, correctAnswerStringRepresentation);
        Question question = factory.createQuestion(dto, null);

        assertTrue(answer.isEqual(question.getCorrectAnswer()));
    }

    @DisplayName("creates SingleSelectQuestion correctly")
    @Test
    void shouldCreateSingleSelectQuestionCorrectly() {
        final int questionId = 1;
        final String questionText = "text?";
        final String correctAnswerStringRepresentation = "1";
        final List<Option> options = createTestOptions();

        QuestionFactory factory = new QuestionFactoryImpl();
        QuestionDto dto = QuestionDto.create(questionId, questionText, QuestionFactoryImpl.SINGLE_SELECT_QUESTION_TYPE_ID, correctAnswerStringRepresentation);
        Question question = factory.createQuestion(dto, options);

        assertAll("question",
                () -> assertEquals(questionText, question.text),
                () -> assertInstanceOf(SingleSelectQuestion.class, question));
    }

    @DisplayName("binds answer to SingleSelectQuestion correctly")
    @Test
    void shouldBindAnswerToSingleSelectQuestionCorrectly() {
        final int questionId = 1;
        final String questionText = "text?";
        final String correctAnswerStringRepresentation = "1";
        final List<Option> options = createTestOptions();

        final Answer answer = new SingleSelectAnswer(Integer.parseInt(correctAnswerStringRepresentation));

        QuestionFactory factory = new QuestionFactoryImpl();
        QuestionDto dto = QuestionDto.create(questionId, questionText, QuestionFactoryImpl.SINGLE_SELECT_QUESTION_TYPE_ID, correctAnswerStringRepresentation);
        Question question = factory.createQuestion(dto, options);

        assertTrue(answer.isEqual(question.getCorrectAnswer()));
    }

    @DisplayName("creates MultiSelectQuestion correctly")
    @Test
    void shouldCreateMultiSelectQuestionCorrectly() {
        final int questionId = 1;
        final String questionText = "text?";
        final String correctAnswerStringRepresentation = "1,2";
        final List<Option> options = createTestOptions();

        QuestionFactory factory = new QuestionFactoryImpl();
        QuestionDto dto = QuestionDto.create(questionId, questionText, QuestionFactoryImpl.MULTI_SELECT_QUESTION_TYPE_ID, correctAnswerStringRepresentation);
        Question question = factory.createQuestion(dto, options);

        assertAll("question",
                () -> assertEquals(questionText, question.text),
                () -> assertInstanceOf(MultiSelectQuestion.class, question));
    }

    @DisplayName("binds answer to MultiSelectQuestion correctly")
    @Test
    void shouldBindAnswerToMultiSelectQuestionCorrectly() {
        final int questionId = 1;
        final String questionText = "text?";
        final String correctAnswerStringRepresentation = "1,2";
        final List<Option> options = createTestOptions();

        final Answer answer = new MultiSelectAnswer(new int[]{1,2});

        QuestionFactory factory = new QuestionFactoryImpl();
        QuestionDto dto = QuestionDto.create(questionId, questionText, QuestionFactoryImpl.MULTI_SELECT_QUESTION_TYPE_ID, correctAnswerStringRepresentation);
        Question question = factory.createQuestion(dto, options);

        assertTrue(answer.isEqual(question.getCorrectAnswer()));
    }

    @DisplayName("throws Exception on unknown questionTypeUd")
    @Test
    void shouldThrowExceptionOnUnknownQuestionTypeId() {
        QuestionFactory factory = new QuestionFactoryImpl();
        QuestionDto dto = QuestionDto.create(1, "", "random", "1");
        assertThrows(RuntimeException.class, () -> {
            Question question = factory.createQuestion(dto, null);
        }, "Unknown question type");
    }

    private List<Option> createTestOptions() {
        List<Option> options = new ArrayList<>();
        options.add(Option.create(1,1,"1"));
        options.add(Option.create(1,2,"2"));
        options.add(Option.create(1,3,"3"));
        options.add(Option.create(1,4,"4"));

        return options;
    }

}