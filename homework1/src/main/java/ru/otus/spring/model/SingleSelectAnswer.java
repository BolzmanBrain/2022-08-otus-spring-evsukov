package ru.otus.spring.model;

public class SingleSelectAnswer implements Answer {
    private final int optionId;

    public SingleSelectAnswer(int optionId) {
        this.optionId = optionId;
    }

    public static SingleSelectAnswer of(String optionIdString) {
        int optionId = Integer.parseInt(optionIdString);
        return new SingleSelectAnswer(optionId);
    }

    @Override
    public boolean isEqual(Answer otherAnswer) {
        // TODO: exception handling
        SingleSelectAnswer otherSingleSelectAnswer = (SingleSelectAnswer)otherAnswer;
        return this.optionId == otherSingleSelectAnswer.optionId;
    }

    @Override
    public String getStringRepresentation() {
        return String.valueOf(optionId);
    }
}
