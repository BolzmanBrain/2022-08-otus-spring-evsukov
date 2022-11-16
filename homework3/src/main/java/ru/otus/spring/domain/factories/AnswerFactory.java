package ru.otus.spring.domain.factories;

import ru.otus.spring.domain.Answer;

public interface AnswerFactory {
    Answer createAnswer(String answerStringRepresentation);
}
