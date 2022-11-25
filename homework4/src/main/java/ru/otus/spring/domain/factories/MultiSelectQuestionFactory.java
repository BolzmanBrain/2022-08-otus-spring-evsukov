package ru.otus.spring.domain.factories;

import ru.otus.spring.domain.MultiSelectAnswer;
import ru.otus.spring.domain.MultiSelectQuestion;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;

import java.util.Arrays;
import java.util.List;

public class MultiSelectQuestionFactory implements QuestionAbstractFactory {
    @Override
    public MultiSelectQuestion createQuestion(QuestionDto questionDto, List<Option> options) {
        MultiSelectAnswer multiSelectAnswer = createAnswer(questionDto.getCorrectAnswerStringRepresentation());
        return new MultiSelectQuestion(questionDto.getQuestionId(), questionDto.getText(), options, multiSelectAnswer);
    }

    @Override
    public MultiSelectAnswer createAnswer(String answerStringRepresentation) {
        int[] correctOptionIds = Arrays.stream(answerStringRepresentation.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
        return new MultiSelectAnswer(correctOptionIds);
    }
}
