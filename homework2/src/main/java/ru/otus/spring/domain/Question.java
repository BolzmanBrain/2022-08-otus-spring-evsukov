package ru.otus.spring.domain;

import lombok.Getter;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionStatus;

import java.util.List;

public abstract class Question {
    public final int questionId;
    public final String text;

    @Getter
    private final Answer correctAnswer;

    @Getter
    private final List<Option> options;

    @Getter
    private QuestionStatus questionStatus = QuestionStatus.NO_ANSWER;
    private Answer givenAnswer;

    public Question(int questionId, String text, List<Option> options, Answer correctAnswer) {
        this.questionId = questionId;
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public void resetAnswer() {
        givenAnswer = null;
        questionStatus = QuestionStatus.NO_ANSWER;
    }

    public abstract String getAnswerInstruction();

    public abstract boolean giveAnswer(String answerStringRepresentation);

    protected boolean checkAnswerAndSetQuestionStatus(Answer answer) {
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
