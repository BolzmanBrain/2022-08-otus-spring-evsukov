package ru.otus.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class AppProps {
    private String sql_before_filename;
    private String sql_after_filename;
}
