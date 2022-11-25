package ru.otus.spring.domain.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public class Option {
    @CsvBindByPosition(position = 0)
    private int questionId;
    @CsvBindByPosition(position = 1)
    private int optionId;
    @CsvBindByPosition(position = 2)
    private String text;

    // This method is required for testing purposes because creating a public
    // constructor results into opencsv Exception
    public static Option create(int questionId, int optionId, String text) {
        Option option = new Option();
        option.questionId = questionId;
        option.optionId = optionId;
        option.text = text;
        return option;
    }
}
