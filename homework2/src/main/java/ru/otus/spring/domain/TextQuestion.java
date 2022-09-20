package ru.otus.spring.domain;

public class TextQuestion extends Question {

    private static final String ANSWER_INSTRUCTION = "Enter the answer as text.";

    public static TextQuestion createFromStringRepresentation(int questionId, String text, String correctAnswerStringRepresentation) {
        TextAnswer correctAnswer = TextAnswer.of(correctAnswerStringRepresentation);
        return new TextQuestion(questionId, text, correctAnswer);
    }

    public TextQuestion(int questionId, String text, TextAnswer correctAnswer) {
        super(questionId, text, null, correctAnswer);
    }

    @Override
    public String getAnswerInstruction() {
        return ANSWER_INSTRUCTION;
    }

    @Override
    public boolean giveAnswer(String answerStringRepresentation) {
        TextAnswer answer = TextAnswer.of(answerStringRepresentation);
        return checkAnswerAndSetQuestionStatus(answer);
    }
}
