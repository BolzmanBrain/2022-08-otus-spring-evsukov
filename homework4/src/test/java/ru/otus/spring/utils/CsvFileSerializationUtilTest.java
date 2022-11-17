package ru.otus.spring.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;
import ru.otus.spring.services.QuestionFactoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("CsvFileSerializationUtil")
class CsvFileSerializationUtilTest {

    @DisplayName("serializes questions correctly")
    @Test
    void shouldSerializeQuestionsCorrectly() throws Exception {
        FileSerializationUtil serializationUtil = new CsvFileSerializationUtil();
        List<Object> objectList = serializationUtil.serializeFile("questions.csv", QuestionDto.class);
        QuestionDto dto = (QuestionDto)objectList.get(0);

        assertAll("question serialization",
                () -> assertEquals(3, dto.getQuestionId()),
                () -> assertEquals("question_3.text", dto.getText()),
                () -> assertEquals(QuestionFactoryImpl.MULTI_SELECT_QUESTION_TYPE_ID, dto.getQuestionTypeId()),
                () -> assertEquals("1,2", dto.getCorrectAnswerStringRepresentation()),
                () -> assertEquals(1, objectList.size()));
    }

    @DisplayName("serializes options correctly")
    @Test
    void shouldSerializeOptionsCorrectly() throws Exception {
        FileSerializationUtil serializationUtil = new CsvFileSerializationUtil();
        List<Object> objectList = serializationUtil.serializeFile("options.csv", Option.class);
        Option dto = (Option)objectList.get(0);

        assertAll("question serialization",
                () -> assertEquals(3, dto.getQuestionId()),
                () -> assertEquals(1, dto.getOptionId()),
                () -> assertEquals("question_3.option_1.text", dto.getText()),
                () -> assertEquals(1, objectList.size()));
    }

}