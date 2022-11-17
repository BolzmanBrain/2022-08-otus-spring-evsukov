package ru.otus.spring.domain;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MultiSelectAnswer implements Answer {
    private final Set<Integer> optionIds;

    public MultiSelectAnswer(int[] optionIdsArray) {
        optionIds = Arrays.stream(optionIdsArray).boxed().collect(Collectors.toSet());
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
