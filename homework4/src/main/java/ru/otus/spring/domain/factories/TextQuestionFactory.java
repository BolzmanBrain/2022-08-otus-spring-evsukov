package ru.otus.spring.domain.factories;

import ru.otus.spring.domain.TextAnswer;
import ru.otus.spring.domain.TextQuestion;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;

import java.util.List;

public class TextQuestionFactory implements QuestionAbstractFactory {
    @Override
    public TextQuestion createQuestion(QuestionDto questionDto, List<Option> options) {
        TextAnswer textAnswer = createAnswer(questionDto.getCorrectAnswerStringRepresentation());
        return new TextQuestion(questionDto.getQuestionId(), questionDto.getText(), textAnswer);
    }

    @Override
    public TextAnswer createAnswer(String answerStringRepresentation) {
        return new TextAnswer(answerStringRepresentation);
    }
}
