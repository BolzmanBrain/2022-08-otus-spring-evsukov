package ru.otus.spring.presentation;

import ru.otus.spring.domain.dto.TestResults;

public interface TestResultsToStringConvertor {
    String convert(TestResults testResults);
}
