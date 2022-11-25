package ru.otus.spring.domain.factories;

import ru.otus.spring.domain.SingleSelectAnswer;
import ru.otus.spring.domain.SingleSelectQuestion;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;

import java.util.List;

public class SingleSelectQuestionFactory implements QuestionAbstractFactory {
    @Override
    public SingleSelectQuestion createQuestion(QuestionDto questionDto, List<Option> options) {
        SingleSelectAnswer singleSelectAnswer = createAnswer(questionDto.getCorrectAnswerStringRepresentation());
        return new SingleSelectQuestion(questionDto.getQuestionId(), questionDto.getText(), options, singleSelectAnswer);
    }

    @Override
    public SingleSelectAnswer createAnswer(String answerStringRepresentation) {
        int optionId = Integer.parseInt(answerStringRepresentation);
        return new SingleSelectAnswer(optionId);
    }
}
