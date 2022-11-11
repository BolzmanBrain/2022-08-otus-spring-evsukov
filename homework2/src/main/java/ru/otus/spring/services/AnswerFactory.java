package ru.otus.spring.services;

import ru.otus.spring.domain.Answer;

public interface AnswerFactory {
    Answer createAnswer(String answerStringRepresentation);
}
