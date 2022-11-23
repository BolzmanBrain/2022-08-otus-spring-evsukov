package ru.otus.spring.domain;

import lombok.Getter;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionStatus;

import java.util.List;

@Getter
public abstract class Question {
    protected final int questionId;
    protected final String text;
    protected final Answer correctAnswer;
    protected final List<Option> options;
    protected QuestionStatus questionStatus = QuestionStatus.NO_ANSWER;
    protected Answer givenAnswer;

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

    public boolean giveAnswer(Answer answer) {
        givenAnswer = answer;
        boolean isAnswerCorrect = correctAnswer.isEqual(answer);

        if(isAnswerCorrect) {
            questionStatus = QuestionStatus.CORRECT_ANSWER;
        } else {
            questionStatus = QuestionStatus.WRONG_ANSWER;
        }
        return isAnswerCorrect;
    }

    public abstract String getQuestionTypeId();

}
