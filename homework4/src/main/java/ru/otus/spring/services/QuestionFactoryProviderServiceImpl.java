package ru.otus.spring.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.dto.QuestionTypes;
import ru.otus.spring.domain.factories.MultiSelectQuestionFactory;
import ru.otus.spring.domain.factories.QuestionAbstractFactory;
import ru.otus.spring.domain.factories.SingleSelectQuestionFactory;
import ru.otus.spring.domain.factories.TextQuestionFactory;
import ru.otus.spring.exceptions.UnknownQuestionTypeIsProvided;


@Service
public class QuestionFactoryProviderServiceImpl implements QuestionFactoryProviderService {

    @Override
    public QuestionAbstractFactory getQuestionFactory(String questionTypeId) {
        switch (questionTypeId) {
            case QuestionTypes.TEXT_QUESTION:
                return new TextQuestionFactory();

            case QuestionTypes.SINGLE_SELECT_QUESTION:
                return new SingleSelectQuestionFactory();

            case QuestionTypes.MULTI_SELECT_QUESTION:
                return new MultiSelectQuestionFactory();

            default:
                throw new UnknownQuestionTypeIsProvided("Unknown question type", null);

        }
    }
}
