package ru.otus.spring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.User;
import ru.otus.spring.presentation.StudentTestingPresentation;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("StudentTestingService")
class StudentTestingServiceTest {

    @DisplayName(".run() loads and processes questions")
    @Test
    void shouldLoadAndProcessQuestionsWhileRunIsCalled() {
        AppProps props = new AppProps();
        StudentTestingPresentation presentation = mock(StudentTestingPresentation.class);
        User user = new User("a","b");

        Question mockQuestion1 = mock(Question.class);
        Question mockQuestion2 = mock(Question.class);
        List<Question> questions = new ArrayList<>();
        questions.add(mockQuestion1);
        questions.add(mockQuestion2);

        QuestionDao mockDao = mock(QuestionDao.class);
        Mockito.when(mockDao.readQuestions()).thenReturn(questions);

        // create object and call method being tested
        StudentTestingService testingService = new StudentTestingService(presentation, mockDao, props);
        testingService.run(user);

        verify(mockDao).readQuestions();
    }
}