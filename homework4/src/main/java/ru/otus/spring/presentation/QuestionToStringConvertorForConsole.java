package ru.otus.spring.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.Option;
import ru.otus.spring.services.AnswerInstructionProviderService;
import ru.otus.spring.services.LocalizationService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionToStringConvertorForConsole implements QuestionToStringConvertor {
    private final LocalizationService localizationService;
    private final AnswerInstructionProviderService answerInstructionProvider;

    @Override
    public String convertQuestion(Question question) {
        StringBuilder sb = new StringBuilder("");
        List<Option> options = question.getOptions();

        appendAndLocalizeSbWithTrailingNewLine(sb, QUESTION_TITLE, question.getQuestionId());
        appendSbWithTrailingNewLine(sb, question.getText());

        if(options != null && !options.isEmpty()) {
            for(Option option : options) {
                appendSbWithTrailingNewLine(sb, option.getText(), option.getOptionId());
            }
        }
        String answerInstructionMessage = localizationService.localizeMessage(answerInstructionProvider.getAnswerInstruction(question.getQuestionTypeId()));
        sb.append(answerInstructionMessage);
        return sb.toString();
    }

    @Override
    public String convertCorrectAnswer(Question question) {
        return localizationService.localizeMessage(QUESTION_CORRECT_ANSWER, question.getCorrectAnswer().getStringRepresentation());
    }

    private void appendAndLocalizeSbWithTrailingNewLine(StringBuilder sb, String message, Object... args) {
        sb.append(localizationService.localizeMessage(message, args));
        sb.append(System.lineSeparator());
    }

    private void appendSbWithTrailingNewLine(StringBuilder sb, String text, Object... args) {
        sb.append(String.format(text, args));
        sb.append(System.lineSeparator());
    }
    private static final String QUESTION_TITLE = "question.title";
    private static final String QUESTION_CORRECT_ANSWER = "question.correct_answer";
}
