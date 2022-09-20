package ru.otus.spring.model;

public class TextAnswer implements Answer {
    private final String optionText;

    public TextAnswer(String string) {
        this.optionText = string;
    }

    public static TextAnswer of(String string) {
        return new TextAnswer(string);
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
