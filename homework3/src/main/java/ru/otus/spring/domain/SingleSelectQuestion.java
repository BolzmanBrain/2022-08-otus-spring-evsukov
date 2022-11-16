package ru.otus.spring.domain;

import ru.otus.spring.configs.AppMessageCodes;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionStatus;
import ru.otus.spring.domain.factories.AnswerFactory;
import ru.otus.spring.domain.factories.SingleSelectAnswerFactory;

import java.util.List;

public class SingleSelectQuestion extends Question {
    public SingleSelectQuestion(int questionId, String text, List<Option> options, SingleSelectAnswer correctAnswer) {
        super(questionId, text, options, correctAnswer);
    }

    @Override
    public String getAnswerInstruction() {
        return AppMessageCodes.QUESTION_SINGLE_SELECT_QUESTION_ANSWER_INSTRUCTION;
    }

    @Override
    public boolean giveAnswer(String answerStringRepresentation) {
        AnswerFactory answerFactory = new SingleSelectAnswerFactory();
        Answer answer = answerFactory.createAnswer(answerStringRepresentation);
        return checkAnswerAndSetQuestionStatus(answer);
    }

    private boolean checkAnswerAndSetQuestionStatus(Answer answer) {
        givenAnswer = answer;
        boolean isAnswerCorrect = correctAnswer.isEqual(answer);

        if(isAnswerCorrect) {
            questionStatus = QuestionStatus.CORRECT_ANSWER;
        } else {
            questionStatus = QuestionStatus.WRONG_ANSWER;
        }
        return isAnswerCorrect;
    }
}
