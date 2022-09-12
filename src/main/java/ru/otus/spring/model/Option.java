package ru.otus.spring.model;

import com.opencsv.bean.CsvBindByPosition;

public class Option {
    @CsvBindByPosition(position = 0)
    private int questionId;

    @CsvBindByPosition(position = 1)
    private int optionId;

    @CsvBindByPosition(position = 2)
    private String text;

    public String toString() {
        return String.format("Option. question_id: %d, option_id: %d, text: %s",questionId, optionId, text);
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getOptionId() {
        return optionId;
    }

    public String getText() {
        return text;
    }
}
