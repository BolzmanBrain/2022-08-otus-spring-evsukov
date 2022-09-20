package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.*;
import ru.otus.spring.domain.dto.Option;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDaoCsv implements QuestionDao {
    private static final String TEXT_QUESTION_TYPE_ID = "t";
    private static final String SINGLE_SELECT_QUESTION_TYPE_ID = "ss";
    private static final String MULTI_SELECT_QUESTION_TYPE_ID = "ms";

    private final String questionsFilename;
    private final String optionsFilename;

    public QuestionDaoCsv(@Value("${filename.questions}") String questionsFilename, @Value("${filename.options}")String optionsFilename) {
        this.questionsFilename = questionsFilename;
        this.optionsFilename = optionsFilename;
    }

    @Override
    public List<Question> readQuestions() throws Exception {
        List<Option> options = readOptions();
        List<Question> questions = new ArrayList<>();

        File questionsFile = getFileFromResource(questionsFilename);
        FileReader questionsFileReader = new FileReader(questionsFile);
        CSVReader csvReader = new CSVReaderBuilder(questionsFileReader)
                .withSkipLines(1)
                .build();

        String[] lineValues = csvReader.readNext();
        while(lineValues != null) {
            Question newQuestion = createQuestion(lineValues, options);
            questions.add(newQuestion);
            lineValues = csvReader.readNext();
        }
        return questions;
    }

    private Question createQuestion(String[] values, List<Option> options) throws Exception{
        int questionId = Integer.parseInt(values[0]);
        String text = values[1];
        String questionTypeId = values[2];
        String correctAnswerValue = values[3];

        List<Option> filteredOptions;
        Question result;

        switch (questionTypeId) {
            case TEXT_QUESTION_TYPE_ID:
                result = TextQuestion.createFromStringRepresentation(questionId, text, correctAnswerValue);
                break;

            case SINGLE_SELECT_QUESTION_TYPE_ID:
                filteredOptions = filterOptionsByQuestionId(options, questionId);
                result = SingleSelectQuestion.createFromStringRepresentation(questionId, text, filteredOptions, correctAnswerValue);
                break;

            case MULTI_SELECT_QUESTION_TYPE_ID:
                filteredOptions = filterOptionsByQuestionId(options, questionId);
                result = MultiSelectQuestion.createFromStringRepresentation(questionId, text, filteredOptions, correctAnswerValue);
                break;

            default:
                throw new Exception("Неизвестный тип вопроса");
        }
        return result;
    }

    private List<Option> filterOptionsByQuestionId(List<Option> options, int questionId) {
        List<Option> filteredOptions = new ArrayList<>();

        for(Option option : options) {
            if(option.getQuestionId() == questionId) {
                filteredOptions.add(option);
            }
        }
        return filteredOptions;
    }

    private List<Option> readOptions() throws Exception {
        File optionsFile= getFileFromResource(optionsFilename);
        List<Option> options = new CsvToBeanBuilder(new FileReader(optionsFile))
                .withType(Option.class)
                .withSkipLines(1)
                .build()
                .parse();
        return options;
    }

    private File getFileFromResource(String fileName) throws Exception {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(fileName);
            if (resource == null) {
                throw new IllegalArgumentException("file not found! " + fileName);
            } else {
                // failed if files have whitespaces or special characters
                //return new File(resource.getFile());
                return new File(resource.toURI());
            }
         }
        catch (URISyntaxException e) {
            throw new Exception(String.format("File %s not found",fileName));
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
