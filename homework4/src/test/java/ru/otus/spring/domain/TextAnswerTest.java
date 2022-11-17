package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TextAnswer")
class TextAnswerTest {

    @DisplayName("returns String representation correctly")
    @Test
    void shouldReturnStringRepresentationCorrectly() {
        final String VALUE = "Answer's value";

        Answer answer = new TextAnswer(VALUE);
        assertEquals(VALUE, answer.getStringRepresentation());
    }

    @DisplayName("isEqual() works while answers are equal")
    @Test
    void shouldIsEqualWorkCorrectlyWhileAnswersAreEqual() {
        final String VALUE = "Answer's value";

        Answer answer1 = new TextAnswer(VALUE);
        Answer answer2 = new TextAnswer(VALUE);

        assertTrue(answer1.isEqual(answer2));
    }

    @DisplayName("isEqual() works while answers are not equal")
    @Test
    void shouldIsEqualWorkCorrectlyWhileAnswersAreNotEqual() {
        Answer answer1 = new TextAnswer("Answer's value 1");
        Answer answer2 = new TextAnswer("Answer's value 2");

        assertFalse(answer1.isEqual(answer2));
    }
}