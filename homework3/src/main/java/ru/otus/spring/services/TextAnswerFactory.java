package ru.otus.spring.services;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.TextAnswer;

public class TextAnswerFactory implements AnswerFactory {
    @Override
    public TextAnswer createAnswer(String answerStringRepresentation) {
        return new TextAnswer(answerStringRepresentation);
    }
}
