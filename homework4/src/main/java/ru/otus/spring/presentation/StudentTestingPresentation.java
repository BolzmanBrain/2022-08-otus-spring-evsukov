package ru.otus.spring.presentation;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.TestResults;

public interface StudentTestingPresentation {

    void displayQuestion(Question question);

    boolean giveAnswer(Question question, String answerStringRepresentation);

    void displayCorrectAnswer(Question question);

    void showMessage(String message, String... args);

    void showTestResults(TestResults testResults);

    String readUserInput();
}
