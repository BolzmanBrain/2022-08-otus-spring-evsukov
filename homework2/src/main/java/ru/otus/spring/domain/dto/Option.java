package ru.otus.spring.domain.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;

@Getter
public class Option {
    @CsvBindByPosition(position = 0)
    private int questionId;
    @CsvBindByPosition(position = 1)
    private int optionId;
    @CsvBindByPosition(position = 2)
    private String text;
}
