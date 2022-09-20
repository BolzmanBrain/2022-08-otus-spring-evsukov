package ru.otus.spring.model;

import java.util.List;

public class SingleSelectQuestion extends Question {
    private static final String ANSWER_INSTRUCTION = "Select one of the options.";

    public static SingleSelectQuestion createFromStringRepresentation(int questionId, String text, List<Option> options, String correctAnswerStringRepresentation) {
        SingleSelectAnswer correctAnswer = SingleSelectAnswer.of(correctAnswerStringRepresentation);
        return new SingleSelectQuestion(questionId, text, options, correctAnswer);
    }

    public SingleSelectQuestion(int questionId, String text, List<Option> options, SingleSelectAnswer correctAnswer) {
        super(questionId, text, options, correctAnswer);
    }

    @Override
    public String getAnswerInstruction() {
        return ANSWER_INSTRUCTION;
    }

    @Override
    public boolean giveAnswer(String answerStringRepresentation) {
        SingleSelectAnswer answer = SingleSelectAnswer.of(answerStringRepresentation);
        return checkAnswerAndSetQuestionStatus(answer);
    }
}
