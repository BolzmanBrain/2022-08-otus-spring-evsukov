package ru.otus.spring.domain;

import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionTypes;

import java.util.List;

public class SingleSelectQuestion extends Question {
    public SingleSelectQuestion(int questionId, String text, List<Option> options, SingleSelectAnswer correctAnswer) {
        super(questionId, text, options, correctAnswer);
    }

    @Override
    public String getQuestionTypeId() {
        return QuestionTypes.SINGLE_SELECT_QUESTION;
    }
}
