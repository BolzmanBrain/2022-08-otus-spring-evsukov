package ru.otus.spring.services;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;

import java.util.List;

public interface QuestionFactory {
    Question createQuestion(QuestionDto questionDto, List<Option> options);
}
