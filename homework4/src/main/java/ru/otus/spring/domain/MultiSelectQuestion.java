package ru.otus.spring.domain;

import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionTypes;

import java.util.List;

public class MultiSelectQuestion extends Question {
    public MultiSelectQuestion(int questionId, String text, List<Option> options, MultiSelectAnswer correctAnswer) {
        super(questionId, text, options, correctAnswer);
    }

    @Override
    public String getQuestionTypeId() {
        return QuestionTypes.MULTI_SELECT_QUESTION;
    }
}
