package ru.otus.spring.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class AppProps {

    private String options_filename;

    private String questions_filename;

    private double percent_of_correct_answers_required;

    private Locale locale;
}
