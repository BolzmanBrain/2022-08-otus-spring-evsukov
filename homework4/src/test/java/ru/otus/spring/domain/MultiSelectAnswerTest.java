package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MultiSelectAnswer")
class MultiSelectAnswerTest {
    @DisplayName("returns String representation correctly")
    @Test
    void shouldReturnStringRepresentationCorrectly() {
        Answer answer = new MultiSelectAnswer(new int[]{1,2});
        assertEquals("1,2", answer.getStringRepresentation());
    }

    @DisplayName("isEqual() works correctly while answers are equal")
    @Test
    void shouldIsEqualWorkCorrectlyWhileAnswersAreEqual() {
        Answer answer1 = new MultiSelectAnswer(new int[]{1,2,3});
        Answer answer2 = new MultiSelectAnswer(new int[]{1,2,3});

        assertTrue(answer1.isEqual(answer2));
    }

    @DisplayName("isEqual() works correctly while answers are not equal")
    @Test
    void shouldIsEqualWorkCorrectlyWhileAnswersAreNotEqual() {
        Answer answer1 = new MultiSelectAnswer(new int[]{1,2,3});
        Answer answer2 = new MultiSelectAnswer(new int[]{1,2,4});

        assertFalse(answer1.isEqual(answer2));
    }
}