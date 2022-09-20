package ru.otus.spring.model;

import java.util.List;

public abstract class Question {
    public final int questionId;
    public final String text;
    private final Answer correctAnswer;
    private final List<Option> options;

    private QuestionStatus questionStatus = QuestionStatus.NO_ANSWER;
    private Answer givenAnswer;

    public Question(int questionId, String text, List<Option> options, Answer correctAnswer) {
        this.questionId = questionId;
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public QuestionStatus getQuestionStatus() {
        return questionStatus;
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
