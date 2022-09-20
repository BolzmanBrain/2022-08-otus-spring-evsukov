package ru.otus.spring.presentation;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class QuestionPresentationConsole implements QuestionPresentation {

    private final BufferedReader consoleReader;

    public QuestionPresentationConsole() {
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void displayQuestion(Question question) {
        List<Option> options = question.getOptions();

        System.out.println("\nQuestion: " + question.questionId);
        System.out.println(question.text);

        if(options != null && !options.isEmpty()) {
            for(Option option : options) {
                System.out.println(option.getOptionId() + " - " + option.getText());
            }
        }
        System.out.println(question.getAnswerInstruction());
    }

    @Override
    public boolean giveAnswer(Question question, String answerStringRepresentation) {
        boolean isCorrectAnswer = question.giveAnswer(answerStringRepresentation);
        return isCorrectAnswer;
    }

    @Override
    public void displayCorrectAnswer(Question question) {
        System.out.println("Correct answer is " + question.getCorrectAnswer().getStringRepresentation());
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String readUserInput() {
        String result = "";
        try {
            result = consoleReader.readLine();
        }
        catch (Exception e) {
            System.out.println("Failed to read user input.");
        }
        return result;
    }
}
