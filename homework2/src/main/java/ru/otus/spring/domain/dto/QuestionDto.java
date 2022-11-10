package ru.otus.spring.domain.dto;

import java.util.List;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
public class QuestionDto {
    @CsvBindByPosition(position = 0)
    private int questionId;

    @CsvBindByPosition(position = 1)
    private String text;

    @CsvBindByPosition(position = 2)
    private String questionTypeId;

    @CsvBindByPosition(position = 3)
    private String correctAnswerStringRepresentation;
}
