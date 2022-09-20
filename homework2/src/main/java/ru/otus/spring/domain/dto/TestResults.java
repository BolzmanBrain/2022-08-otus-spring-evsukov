package ru.otus.spring.domain.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestResults {
    private final int totalQuestions;
    private final int correctAnswerQuestions;
    private final int wrongAnswerQuestions;
    private final int noAnswerQuestions;

    public String getStringRepresentation() {
        return String.format("\nTotal questions: %d\n" +
                        "Questions with a correct answer: %d\n" +
                        "Questions with a wrong answer: %d\n" +
                        "Questions with no answer: %d",
                totalQuestions, correctAnswerQuestions, wrongAnswerQuestions, noAnswerQuestions);
    }

    public double getPercentOfCorrectAnswers() {
        return 100 * correctAnswerQuestions / totalQuestions;
    }
}
