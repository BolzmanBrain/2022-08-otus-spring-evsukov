package ru.otus.spring.domain;

import java.util.HashSet;
import java.util.Set;

public class MultiSelectAnswer implements Answer {
    private final Set<Integer> optionIds;

    public MultiSelectAnswer(int[] optionIdsArray) {
        optionIds = new HashSet<>();

        for (int optionId: optionIdsArray) {
            optionIds.add(optionId);
        }
    }

    public static MultiSelectAnswer of(String commaSeparatedString) {
        String[] correctOptionIdsString = commaSeparatedString.split(",");
        int[] correctOptionIds = new int[correctOptionIdsString.length];

        for(int i = 0; i < correctOptionIdsString.length; i++) {
            correctOptionIds[i] = Integer.parseInt(correctOptionIdsString[i]);
        }
        return new MultiSelectAnswer(correctOptionIds);
    }

    @Override
    public boolean isEqual(Answer otherAnswer) {
        // TODO: exception handling
        MultiSelectAnswer otherMultiSelectAnswer = (MultiSelectAnswer)otherAnswer;
        return this.optionIds.equals(otherMultiSelectAnswer.optionIds);
    }

    @Override
    public String getStringRepresentation() {
        Integer[] optionIdArr = optionIds.toArray(new Integer[0]);
        String [] optionIdStrArr = new String[optionIdArr.length];

        for(int i = 0; i < optionIdArr.length; i++) {
            optionIdStrArr[i] = Integer.toString(optionIdArr[i]);
        }
        String commaSeparatedString = String.join(",", optionIdStrArr);
        return commaSeparatedString;
    }
}
