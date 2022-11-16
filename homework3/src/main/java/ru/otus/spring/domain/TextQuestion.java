package ru.otus.spring.domain;

import ru.otus.spring.configs.AppMessageCodes;
import ru.otus.spring.domain.dto.QuestionStatus;
import ru.otus.spring.domain.factories.AnswerFactory;
import ru.otus.spring.domain.factories.TextAnswerFactory;

public class TextQuestion extends Question {

    public TextQuestion(int questionId, String text, TextAnswer correctAnswer) {
        super(questionId, text, null, correctAnswer);
    }

    @Override
    public String getAnswerInstruction() {
        return AppMessageCodes.QUESTION_TEXT_QUESTION_ANSWER_INSTRUCTION;
    }

    @Override
    public boolean giveAnswer(String answerStringRepresentation) {
        AnswerFactory answerFactory = new TextAnswerFactory();
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
