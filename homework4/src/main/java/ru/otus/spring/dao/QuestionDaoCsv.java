package ru.otus.spring.dao;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.configs.ResourceFilesProvider;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.dto.QuestionDto;
import ru.otus.spring.domain.factories.QuestionAbstractFactory;
import ru.otus.spring.exceptions.FailedToSerializeResource;
import ru.otus.spring.services.QuestionFactoryProviderService;
import ru.otus.spring.utils.FileSerializationUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDaoCsv implements QuestionDao {
    private final ResourceFilesProvider resourceFilesProvider;
    private final FileSerializationUtil serializationUtil;
    private final QuestionFactoryProviderService questionFactoryProvider;

    @Autowired
    public QuestionDaoCsv(ResourceFilesProvider resourceFilesProvider, FileSerializationUtil serializationUtil, QuestionFactoryProviderService questionFactoryProvider) {
        this.resourceFilesProvider = resourceFilesProvider;
        this.serializationUtil = serializationUtil;
        this.questionFactoryProvider = questionFactoryProvider;
    }

    @Override
    public List<Question> readQuestions() {
        List<Option> options = readOptions();
        List<Option> optionsForCurrentQuestion;
        List<Question> questions = new ArrayList<>();
        List<QuestionDto> questionDtos;

        try {
            questionDtos = (List<QuestionDto>)(List<?>)serializationUtil.serializeFile(resourceFilesProvider.getQuestionsFileString(), QuestionDto.class);
        }catch (Exception e) {
            throw new FailedToSerializeResource("Failed to serialize questions",e);
        }

        for(QuestionDto questionDto : questionDtos) {
            optionsForCurrentQuestion = getOptionsForQuestionId(options, questionDto.getQuestionId());
            Question question = createQuestion(questionDto, optionsForCurrentQuestion);
            questions.add(question);
        }
        return questions;
    }

    private Question createQuestion(QuestionDto dto, List<Option> options) {
        QuestionAbstractFactory factory = questionFactoryProvider.getQuestionFactory(dto.getQuestionTypeId());
        return factory.createQuestion(dto, options);
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

    private List<Option> readOptions() {
        try {
            List<Option> options = (List<Option>)(List<?>)serializationUtil.serializeFile(resourceFilesProvider.getOptionsFileString(), Option.class);
            return options;
        }
        catch (Exception e) {
            throw new FailedToSerializeResource("Failed to serialize options",e);
        }
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
