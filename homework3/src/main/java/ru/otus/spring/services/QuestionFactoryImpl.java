package ru.otus.spring.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.*;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;
import ru.otus.spring.domain.factories.AnswerFactory;
import ru.otus.spring.domain.factories.MultiSelectAnswerFactory;
import ru.otus.spring.domain.factories.SingleSelectAnswerFactory;
import ru.otus.spring.domain.factories.TextAnswerFactory;

import java.util.List;


@Service
public class QuestionFactoryImpl implements QuestionFactory {
    private static final String TEXT_QUESTION_TYPE_ID = "t";
    private static final String SINGLE_SELECT_QUESTION_TYPE_ID = "ss";
    private static final String MULTI_SELECT_QUESTION_TYPE_ID = "ms";

    @Override
    public Question createQuestion(QuestionDto questionDto, List<Option> options) {
        String questionTypeId = questionDto.getQuestionTypeId();

        Question result;
        AnswerFactory answerFactory;

        switch (questionTypeId) {
            case TEXT_QUESTION_TYPE_ID:
                answerFactory = new TextAnswerFactory();
                TextAnswer textAnswer = (TextAnswer)answerFactory.createAnswer(questionDto.getCorrectAnswerStringRepresentation());
                result = new TextQuestion(questionDto.getQuestionId(), questionDto.getText(), textAnswer);
                break;

            case SINGLE_SELECT_QUESTION_TYPE_ID:
                answerFactory = new SingleSelectAnswerFactory();
                SingleSelectAnswer singleSelectAnswer = (SingleSelectAnswer)answerFactory.createAnswer(questionDto.getCorrectAnswerStringRepresentation());
                result = new SingleSelectQuestion(questionDto.getQuestionId(), questionDto.getText(), options, singleSelectAnswer);
                break;

            case MULTI_SELECT_QUESTION_TYPE_ID:
                answerFactory = new MultiSelectAnswerFactory();
                MultiSelectAnswer multiSelectAnswer = (MultiSelectAnswer)answerFactory.createAnswer(questionDto.getCorrectAnswerStringRepresentation());
                result = new MultiSelectQuestion(questionDto.getQuestionId(), questionDto.getText(), options, multiSelectAnswer);
                break;

            default:
                throw new RuntimeException("Неизвестный тип вопроса");
        }
        return result;
    }
}
