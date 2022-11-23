package ru.otus.spring.services;

import ru.otus.spring.domain.factories.QuestionAbstractFactory;

public interface QuestionFactoryProviderService {
    QuestionAbstractFactory getQuestionFactory(String questionTypeId);
}
