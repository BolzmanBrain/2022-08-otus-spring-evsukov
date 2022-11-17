package ru.otus.spring.domain.dto;

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

    // This method is required for testing purposes because creating a public
    // constructor results into opencsv Exception
    public static QuestionDto create(int questionId, String text, String questionTypeId, String correctAnswerStringRepresentation) {
        QuestionDto questionDto = new QuestionDto();

        questionDto.questionId = questionId;
        questionDto.text = text;
        questionDto.questionTypeId = questionTypeId;
        questionDto.correctAnswerStringRepresentation = correctAnswerStringRepresentation;

        return questionDto;
    }
}
