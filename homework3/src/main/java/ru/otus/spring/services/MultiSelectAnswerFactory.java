package ru.otus.spring.services;

import ru.otus.spring.domain.MultiSelectAnswer;

public class MultiSelectAnswerFactory implements AnswerFactory {
    @Override
    public MultiSelectAnswer createAnswer(String answerStringRepresentation) {
        String[] correctOptionIdsString = answerStringRepresentation.split(",");
        int[] correctOptionIds = new int[correctOptionIdsString.length];

        for(int i = 0; i < correctOptionIdsString.length; i++) {
            correctOptionIds[i] = Integer.parseInt(correctOptionIdsString[i]);
        }
        return new MultiSelectAnswer(correctOptionIds);
    }
}
