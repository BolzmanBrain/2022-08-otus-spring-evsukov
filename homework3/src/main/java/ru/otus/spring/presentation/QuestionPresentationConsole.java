package ru.otus.spring.presentation;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.configs.AppMessageCodes;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.TestResults;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class QuestionPresentationConsole implements QuestionPresentation {

    private final MessageSource messageSource;
    private final BufferedReader consoleReader;
    private final AppProps appProps;

    public QuestionPresentationConsole(MessageSource messageSource, AppProps appProps) {
        this.appProps = appProps;
        this.messageSource = messageSource;
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void displayQuestion(Question question) {
        List<Option> options = question.getOptions();

        println(AppMessageCodes.QUESTION_TITLE, question.questionId);
        println(question.text);

        if(options != null && !options.isEmpty()) {
            for(Option option : options) {
                println(option.getText(), option.getOptionId());
            }
        }
        println(question.getAnswerInstruction());
    }

    @Override
    public boolean giveAnswer(Question question, String answerStringRepresentation) {
        return question.giveAnswer(answerStringRepresentation);
    }

    @Override
    public void displayCorrectAnswer(Question question) {
        println(AppMessageCodes.QUESTION_CORRECT_ANSWER, question.getCorrectAnswer().getStringRepresentation());
    }

    @Override
    public void showMessage(String message, String... args) {
        println(message, args);
    }

    @Override
    public void showTestResults(TestResults testResults) {
        System.out.println();
        println(AppMessageCodes.TEST_RESULTS_TOTAL_QUESTIONS, testResults.totalQuestions);
        println(AppMessageCodes.TEST_RESULTS_CORRECT_ANSWERS, testResults.correctAnswerQuestions);
        println(AppMessageCodes.TEST_RESULTS_WRONG_ANSWERS, testResults.wrongAnswerQuestions);
        println(AppMessageCodes.TEST_RESULTS_NOT_GIVEN_ANSWERS, testResults.noAnswerQuestions);
    }

    @Override
    public String readUserInput() {
        String result = "";
        try {
            result = consoleReader.readLine();
        }
        catch (Exception e) {
            println(AppMessageCodes.QUESTION_EXCEPTION_USER_INPUT);
        }
        return result;
    }

    private void println(String code, Object... args) {
        String str = messageSource.getMessage(code, args, appProps.getLocale());
        System.out.println(str);
    }
}
