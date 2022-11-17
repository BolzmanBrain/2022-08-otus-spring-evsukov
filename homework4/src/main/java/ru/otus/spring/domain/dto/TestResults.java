package ru.otus.spring.domain.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestResults {
    public final int totalQuestions;
    public final int correctAnswerQuestions;
    public final int wrongAnswerQuestions;
    public final int noAnswerQuestions;

    public double getPercentOfCorrectAnswers() {
        return 100.0 * correctAnswerQuestions / totalQuestions;
    }
}
