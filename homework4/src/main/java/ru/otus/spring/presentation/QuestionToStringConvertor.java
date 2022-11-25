package ru.otus.spring.presentation;

import ru.otus.spring.domain.Question;

public interface QuestionToStringConvertor {
    String convertQuestion(Question question);
    String convertCorrectAnswer(Question question);
}
