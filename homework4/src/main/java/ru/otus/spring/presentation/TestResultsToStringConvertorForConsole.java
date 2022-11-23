package ru.otus.spring.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.dto.TestResults;
import ru.otus.spring.services.LocalizationService;

@Component
@RequiredArgsConstructor
public class TestResultsToStringConvertorForConsole implements TestResultsToStringConvertor {
    private final LocalizationService localizationService;

    @Override
    public String convert(TestResults testResults) {
        StringBuilder sb = new StringBuilder("");
        appendAndLocalizeSbWithTrailingNewLine(sb, TEST_RESULTS_TOTAL_QUESTIONS, testResults.totalQuestions);
        appendAndLocalizeSbWithTrailingNewLine(sb, TEST_RESULTS_CORRECT_ANSWERS, testResults.correctAnswerQuestions);
        appendAndLocalizeSbWithTrailingNewLine(sb, TEST_RESULTS_WRONG_ANSWERS, testResults.wrongAnswerQuestions);
        appendAndLocalizeSbWithTrailingNewLine(sb, TEST_RESULTS_NOT_GIVEN_ANSWERS, testResults.noAnswerQuestions);
        return sb.toString();
    }

    private void appendAndLocalizeSbWithTrailingNewLine(StringBuilder sb, String message, Object... args) {
        sb.append(localizationService.localizeMessage(message, args));
        sb.append(System.lineSeparator());
    }

    private static final String TEST_RESULTS_TOTAL_QUESTIONS = "test_results.total_questions";
    private static final String TEST_RESULTS_CORRECT_ANSWERS = "test_results.correct_answers";
    private static final String TEST_RESULTS_WRONG_ANSWERS = "test_results.wrong_answers";
    private static final String TEST_RESULTS_NOT_GIVEN_ANSWERS = "test_results.not_given_answers";
}
