package ru.otus.spring.domain.factories;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;

import java.util.List;

public interface QuestionAbstractFactory {
    Question createQuestion(QuestionDto questionDto, List<Option> options);
    Answer createAnswer(String answerStringRepresentation);
}
