package ru.otus.spring.domain;

import ru.otus.spring.domain.dto.Option;

import java.util.List;

public class MultiSelectQuestion extends Question {
    private static final String ANSWER_INSTRUCTION = "Select one or more options";

    public static MultiSelectQuestion createFromStringRepresentation(int questionId, String text, List<Option> options, String correctAnswerStringRepresentation) {
        MultiSelectAnswer correctAnswer = MultiSelectAnswer.of(correctAnswerStringRepresentation);
        return new MultiSelectQuestion(questionId, text, options, correctAnswer);
    }

    public MultiSelectQuestion(int questionId, String text, List<Option> options, MultiSelectAnswer correctAnswer) {
        super(questionId, text, options, correctAnswer);
    }

    @Override
    public String getAnswerInstruction() {
        return ANSWER_INSTRUCTION;
    }

    @Override
    public boolean giveAnswer(String answerStringRepresentation) {
        MultiSelectAnswer answer = MultiSelectAnswer.of(answerStringRepresentation);
        return checkAnswerAndSetQuestionStatus(answer);
    }
}
