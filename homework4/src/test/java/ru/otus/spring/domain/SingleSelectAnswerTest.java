package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SingleSelectAnswer")
class SingleSelectAnswerTest {
    @DisplayName("returns String representation correctly")
    @Test
    void shouldReturnStringRepresentationCorrectly() {
        final int VALUE = 1;

        Answer answer = new SingleSelectAnswer(VALUE);
        assertEquals(Integer.toString(VALUE), answer.getStringRepresentation());
    }

    @DisplayName("isEqual() works correctly while answers are equal")
    @Test
    void shouldIsEqualWorkCorrectlyWhileAnswersAreEqual() {
        final int VALUE = 1;

        Answer answer1 = new SingleSelectAnswer(VALUE);
        Answer answer2 = new SingleSelectAnswer(VALUE);

        assertTrue(answer1.isEqual(answer2));
    }

    @DisplayName("isEqual() works correctly while answers are not equal")
    @Test
    void shouldIsEqualWorkCorrectlyWhileAnswersAreNotEqual() {
        Answer answer1 = new SingleSelectAnswer(1);
        Answer answer2 = new SingleSelectAnswer(2);

        assertFalse(answer1.isEqual(answer2));
    }
}