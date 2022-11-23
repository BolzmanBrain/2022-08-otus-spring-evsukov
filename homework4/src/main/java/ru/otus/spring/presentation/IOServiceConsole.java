package ru.otus.spring.presentation;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class IOServiceConsole implements IOService {
    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String readInput() {
        String result = "";
        try {
            result = consoleReader.readLine();
        }
        catch (Exception e) {
            printLine(QUESTION_EXCEPTION_USER_INPUT);
        }
        return result;
    }

    @Override
    public void printLine(String str) {
        System.out.println(str);
    }
    private static final String QUESTION_EXCEPTION_USER_INPUT = "question.exception_user_input";
}
