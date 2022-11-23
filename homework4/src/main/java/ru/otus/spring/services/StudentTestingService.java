package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.QuestionStatus;
import ru.otus.spring.domain.dto.TestResults;
import ru.otus.spring.domain.dto.User;
import ru.otus.spring.domain.factories.QuestionAbstractFactory;
import ru.otus.spring.presentation.IOService;
import ru.otus.spring.presentation.QuestionToStringConvertor;
import ru.otus.spring.presentation.TestResultsToStringConvertor;

import java.util.ArrayList;
import java.util.List;

// The "CommandLineRunner" is implemented to make this class run when
// SpringApplication.run is called in the class annotated with @SpringBootApplication
@Service
@RequiredArgsConstructor
public class StudentTestingService {
    private final IOService ioService;
    private final QuestionDao questionDao;
    private final AppProps appProps;
    private final QuestionToStringConvertor questionToStringConvertor;
    private final TestResultsToStringConvertor testResultsToStringConvertor;
    private final LocalizationService localizationService;
    private final QuestionFactoryProviderService factoryProvider;
    private List<Question> questions;

    public void run(User user) {
        tryPrepareQuestions();
        localizeAndPrint(TEST_START);

        for(Question question : questions) {
            processQuestion(question);
        }
        localizeAndPrint(TEST_FINISH, user.getStringRepresentation());
        ioService.printLine("");
        reportTestResults();
    }

    private void reportTestResults() {
        int totalQuestions = questions.size();
        int correctAnswerQuestions = filterQuestionsByStatus(QuestionStatus.CORRECT_ANSWER).size();
        int wrongAnswerQuestions = filterQuestionsByStatus(QuestionStatus.WRONG_ANSWER).size();
        int noAnswerQuestions = filterQuestionsByStatus(QuestionStatus.NO_ANSWER).size();

        TestResults testResults = new TestResults(totalQuestions, correctAnswerQuestions, wrongAnswerQuestions, noAnswerQuestions);
        String testResultsString = testResultsToStringConvertor.convert(testResults);
        ioService.printLine(testResultsString);

        if(testResults.getPercentOfCorrectAnswers() >= appProps.getPercent_of_correct_answers_required()) {
            localizeAndPrint(TEST_PASSED);
        } else {
            localizeAndPrint(TEST_FAILED);
        }
    }

    private void tryPrepareQuestions() {
        try {
            questions = questionDao.readQuestions();
        } catch (Exception e) {
            localizeAndPrint(TEST_EXCEPTION_LOAD_QUESTIONS);
        }
    }

    private void processQuestion(Question question) {
        String questionString = questionToStringConvertor.convertQuestion(question);
        ioService.printLine(questionString);

        String answerStringRepresentation = ioService.readInput();
        Answer answer = createAnswerFromStringRepresentation(answerStringRepresentation, question.getQuestionTypeId());
        boolean isCorrectAnswer = question.giveAnswer(answer);

        if(isCorrectAnswer) {
            localizeAndPrint(TEST_ANSWER_IS_CORRECT);
        } else {
            localizeAndPrint(TEST_ANSWER_IS_WRONG);

            String correctAnswerString = questionToStringConvertor.convertCorrectAnswer(question);
            ioService.printLine(correctAnswerString);
        }
    }

    private Answer createAnswerFromStringRepresentation(String answerStringRepresentation, String questionTypeId) {
        QuestionAbstractFactory factory = factoryProvider.getQuestionFactory(questionTypeId);
        return factory.createAnswer(answerStringRepresentation);
    }

    private List<Question> filterQuestionsByStatus(QuestionStatus status) {
        List<Question> filteredQuestions = new ArrayList<>();

        for(Question question : questions) {
            if(question.getQuestionStatus() == status) {
                filteredQuestions.add(question);
            }
        }
        return filteredQuestions;
    }

    private void localizeAndPrint(String message, Object... args) {
        ioService.printLine(localizationService.localizeMessage(message, args));
    }

    private static final String TEST_START = "test.start";
    private static final String TEST_FINISH = "test.finish";
    private static final String TEST_PASSED = "test.passed";
    private static final String TEST_FAILED = "test.failed";
    private static final String TEST_EXCEPTION_LOAD_QUESTIONS = "test.exception_load_questions";
    private static final String TEST_ANSWER_IS_CORRECT = "test.answer_is_correct";
    private static final String TEST_ANSWER_IS_WRONG = "test.answer_is_wrong";
}
