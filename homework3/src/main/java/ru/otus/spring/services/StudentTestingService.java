package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.otus.spring.configs.AppMessageCodes;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.QuestionStatus;
import ru.otus.spring.domain.dto.TestResults;
import ru.otus.spring.domain.dto.User;
import ru.otus.spring.presentation.QuestionPresentation;

import java.util.ArrayList;
import java.util.List;

// The "CommandLineRunner" is implemented to make this class run when
// SpringApplication.run is called in the class annotated with @SpringBootApplication
@Service
public class StudentTestingService implements CommandLineRunner {
    private final QuestionPresentation questionPresentation;
    private final QuestionDao questionDao;
    private TestResults testResults;
    private List<Question> questions;
    private User user;

    private final AppProps appProps;

    @Autowired
    public StudentTestingService(QuestionPresentation questionPresentation, QuestionDao questionDao, AppProps appProps) {
        this.questionPresentation = questionPresentation;
        this.questionDao = questionDao;
        this.appProps = appProps;
    }

    @Override
    public void run(String... args) {
        requestUserData();
        tryPrepareQuestions();
        questionPresentation.showMessage(AppMessageCodes.TEST_START);

        for(Question question : questions) {
            processQuestion(question);
        }
        questionPresentation.showMessage(AppMessageCodes.TEST_FINISH, user.getStringRepresentation());
        reportTestResults();
    }

    private void requestUserData() {
        questionPresentation.showMessage(AppMessageCodes.TEST_ENTER_SURNAME);
        String surname = questionPresentation.readUserInput();

        questionPresentation.showMessage(AppMessageCodes.TEST_ENTER_NAME);
        String name = questionPresentation.readUserInput();

        user = new User(name, surname);
    }

    private void reportTestResults() {
        int totalQuestions = questions.size();
        int correctAnswerQuestions = filterQuestionsByStatus(QuestionStatus.CORRECT_ANSWER).size();
        int wrongAnswerQuestions = filterQuestionsByStatus(QuestionStatus.WRONG_ANSWER).size();
        int noAnswerQuestions = filterQuestionsByStatus(QuestionStatus.NO_ANSWER).size();

        testResults = new TestResults(totalQuestions, correctAnswerQuestions, wrongAnswerQuestions, noAnswerQuestions);

        questionPresentation.showTestResults(testResults);

        if(testResults.getPercentOfCorrectAnswers() >= appProps.getPercent_of_correct_answers_required()) {
            questionPresentation.showMessage(AppMessageCodes.TEST_PASSED);
        } else {
            questionPresentation.showMessage(AppMessageCodes.TEST_FAILED);
        }
    }

    private void tryPrepareQuestions() {
        try {
            questions = questionDao.readQuestions();
        } catch (Exception e) {
            questionPresentation.showMessage(AppMessageCodes.TEST_EXCEPTION_LOAD_QUESTIONS);
        }
    }

    private void processQuestion(Question question) {
        questionPresentation.displayQuestion(question);

        String answerString = questionPresentation.readUserInput();
        boolean isCorrectAnswer = questionPresentation.giveAnswer(question, answerString);

        if(isCorrectAnswer) {
            questionPresentation.showMessage(AppMessageCodes.TEST_ANSWER_IS_CORRECT);
        } else {
            questionPresentation.showMessage(AppMessageCodes.TEST_ANSWER_IS_WRONG);
            questionPresentation.displayCorrectAnswer(question);
        }
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
}
