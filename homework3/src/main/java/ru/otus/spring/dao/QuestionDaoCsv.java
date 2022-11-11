package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;
import ru.otus.spring.services.QuestionFactory;
import ru.otus.spring.utils.FileSerializationUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDaoCsv implements QuestionDao {
    private final AppProps appProps;
    private final FileSerializationUtil serializationUtil;
    private final QuestionFactory questionFactory;

    @Autowired
    public QuestionDaoCsv(AppProps appProps, FileSerializationUtil serializationUtil, QuestionFactory questionFactory) {
        this.appProps = appProps;
        this.serializationUtil = serializationUtil;
        this.questionFactory = questionFactory;
    }

    @Override
    public List<Question> readQuestions() throws Exception {
        List<Option> options = readOptions();
        List<Option> optionsForCurrentQuestion;
        List<QuestionDto> questionDtos = (List<QuestionDto>)(List<?>)serializationUtil.serializeFile(appProps.getQuestions_filename(), QuestionDto.class);
        List<Question> questions = new ArrayList<>();

        for(QuestionDto questionDto : questionDtos) {
            optionsForCurrentQuestion = getOptionsForQuestionId(options, questionDto.getQuestionId());
            Question question = questionFactory.createQuestion(questionDto, optionsForCurrentQuestion);
            questions.add(question);
        }
        return questions;
    }

    private List<Option> getOptionsForQuestionId(List<Option> options, int questionId) {
        List<Option> filteredOptions = new ArrayList<>();
        List<Option> optionsCopy = new ArrayList<>(options);

        for(Option option : optionsCopy) {
            if(option.getQuestionId() == questionId) {
                filteredOptions.add(option);
                options.remove(option);
            }
        }
        return filteredOptions;
    }

    private List<Option> readOptions() throws Exception {
        List<Option> options = (List<Option>)(List<?>)serializationUtil.serializeFile(appProps.getOptions_filename(), Option.class);
        return options;
    }

    private static void printFile(File file) {
        List lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
