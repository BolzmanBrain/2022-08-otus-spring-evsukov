package ru.otus.spring.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.spring.aspects.LogMethod;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
@Setter
@Getter
public class AppProps implements LocaleProvider, ResourceFilesProvider {
    private String options_filename;
    private String questions_filename;
    private double percent_of_correct_answers_required;
    private Locale locale;

    @Override
    public Locale getCurrentLocale() {
        return locale;
    }

    @LogMethod
    @Override
    public String getOptionsFileString() {
        return String.format(options_filename, locale);
    }

    @Override
    public String getQuestionsFileString() {
        return String.format(questions_filename, locale);
    }

}
