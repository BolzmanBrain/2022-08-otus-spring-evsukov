package ru.otus.spring.domain;

import ru.otus.spring.domain.dto.QuestionTypes;

public class TextQuestion extends Question {

    public TextQuestion(int questionId, String text, TextAnswer correctAnswer) {
        super(questionId, text, null, correctAnswer);
    }

    @Override
    public String getQuestionTypeId() {
        return QuestionTypes.TEXT_QUESTION;
    }

}
