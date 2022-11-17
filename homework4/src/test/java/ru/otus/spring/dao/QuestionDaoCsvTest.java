package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;
import ru.otus.spring.services.QuestionFactory;
import ru.otus.spring.utils.FileSerializationUtil;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("QuestionDaoCsv")
class QuestionDaoCsvTest {

    @DisplayName("reads questions correctly")
    @Test
    void shouldReadQuestionsCorrectly() throws Exception {
        List<Object> dtos = createListQuestionDto();
        List<Object> options = createListOptions();

        // create mock for FileSerializationUtil and define its behavior
        FileSerializationUtil mockSerializationUtil = mock(FileSerializationUtil.class);
        Mockito.when(mockSerializationUtil.serializeFile(any(), eq(QuestionDto.class))).thenReturn(dtos);
        Mockito.when(mockSerializationUtil.serializeFile(any(), eq(Option.class))).thenReturn(options);

        QuestionFactory mockFactory = mock(QuestionFactory.class);
        AppProps props = new AppProps();

        // create the object being tested
        QuestionDao dao = new QuestionDaoCsv(props, mockSerializationUtil, mockFactory);

        // call the method being tested
        List<Question> questions = dao.readQuestions();

        // assert
        int expectedNumberOfCalls = dtos.size();
        verify(mockFactory, Mockito.times(expectedNumberOfCalls)).createQuestion(any(),any());
    }

    private List<Object> createListQuestionDto() {
        List<Object> dtos = new ArrayList<>();
        dtos.add(new QuestionDto());
        dtos.add(new QuestionDto());
        dtos.add(new QuestionDto());
        return dtos;
    }

    private List<Object> createListOptions() {
        List<Object> dtos = new ArrayList<>();
        dtos.add(new Option());
        dtos.add(new Option());
        dtos.add(new Option());
        return dtos;
    }
}