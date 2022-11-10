package ru.otus.spring.services;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.SingleSelectAnswer;

public class SingleSelectAnswerFactory implements AnswerFactory {
    @Override
    public SingleSelectAnswer createAnswer(String answerStringRepresentation) {
        int optionId = Integer.parseInt(answerStringRepresentation);
        return new SingleSelectAnswer(optionId);
    }
}
