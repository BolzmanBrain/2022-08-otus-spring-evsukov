package ru.otus.spring.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppProps {
    @Getter
    @Setter
    private String options_filename;

    @Getter
    @Setter
    private String questions_filename;

    @Getter
    @Setter
    private double percent_of_correct_answers_required;

    @Getter
    @Setter
    private Locale locale;
}
