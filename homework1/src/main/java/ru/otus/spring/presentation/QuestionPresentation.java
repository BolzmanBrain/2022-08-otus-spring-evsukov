package ru.otus.spring.presentation;

import ru.otus.spring.model.Question;

public interface QuestionPresentation {

    void displayQuestion(Question question);

    boolean giveAnswer(Question question, String answerStringRepresentation);

    void displayCorrectAnswer(Question question);

    void showMessage(String message);

    String readUserInput();
}
