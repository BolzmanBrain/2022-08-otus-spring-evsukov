package ru.otus.spring.services;

import ru.otus.spring.domain.Answer;

//TODO: перенсти в utils
public interface AnswerFactory {
    Answer createAnswer(String answerStringRepresentation);
}
