package ru.otus.spring.domain;

public class TextAnswer implements Answer {
    private final String optionText;

    public TextAnswer(String string) {
        this.optionText = string;
    }

    @Override
    public boolean isEqual(Answer otherAnswer) {
        TextAnswer otherTextAnswer = (TextAnswer)otherAnswer;
        return this.optionText.equals(otherTextAnswer.optionText);
    }

    @Override
    public String getStringRepresentation() {
        return optionText;
    }
}
