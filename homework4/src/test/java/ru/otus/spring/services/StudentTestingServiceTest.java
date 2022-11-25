package ru.otus.spring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.User;
import ru.otus.spring.domain.factories.TextQuestionFactory;
import ru.otus.spring.presentation.IOService;
import ru.otus.spring.presentation.QuestionToStringConvertor;
import ru.otus.spring.presentation.TestResultsToStringConvertor;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("StudentTestingService")
class StudentTestingServiceTest {

    @DisplayName(".run() loads and processes questions")
    @Test
    void shouldLoadAndProcessQuestionsWhileRunIsCalled() {
        var props = new AppProps();
        var user = new User("a","b");

        var mockQuestion1 = mock(Question.class);
        var mockQuestion2 = mock(Question.class);
        var questions = new ArrayList<Question>();
        questions.add(mockQuestion1);
        questions.add(mockQuestion2);

        var mockDao = mock(QuestionDao.class);
        Mockito.when(mockDao.readQuestions()).thenReturn(questions);

        var mockIoService = mock(IOService.class);
        var mockQuestionConvertor = mock(QuestionToStringConvertor.class);
        var testResultsConvertor = mock(TestResultsToStringConvertor.class);
        var localizationService = mock(LocalizationService.class);
        var questionFactoryProvider = mock(QuestionFactoryProviderService.class);
        Mockito.when(questionFactoryProvider.getQuestionFactory(any())).thenReturn(new TextQuestionFactory());

        // create object and call method being tested
        StudentTestingService testingService = new StudentTestingService(mockIoService, mockDao, props, mockQuestionConvertor, testResultsConvertor, localizationService, questionFactoryProvider);
        testingService.run(user);

        verify(mockDao).readQuestions();
    }
}