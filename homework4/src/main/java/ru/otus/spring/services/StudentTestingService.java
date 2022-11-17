package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;
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
import ru.otus.spring.presentation.StudentTestingPresentation;

import java.util.ArrayList;
import java.util.List;

// The "CommandLineRunner" is implemented to make this class run when
// SpringApplication.run is called in the class annotated with @SpringBootApplication
@Service
@RequiredArgsConstructor
public class StudentTestingService {
    private final StudentTestingPresentation studentTestingPresentation;
    private final QuestionDao questionDao;
    private final AppProps appProps;
    private TestResults testResults;
    private List<Question> questions;
    private User user;

    public void run(User user) {
        this.user = user;

        tryPrepareQuestions();
        studentTestingPresentation.showMessage(AppMessageCodes.TEST_START);

        for(Question question : questions) {
            processQuestion(question);
        }
        studentTestingPresentation.showMessage(AppMessageCodes.TEST_FINISH, user.getStringRepresentation());
        reportTestResults();
    }

    private void reportTestResults() {
        int totalQuestions = questions.size();
        int correctAnswerQuestions = filterQuestionsByStatus(QuestionStatus.CORRECT_ANSWER).size();
        int wrongAnswerQuestions = filterQuestionsByStatus(QuestionStatus.WRONG_ANSWER).size();
        int noAnswerQuestions = filterQuestionsByStatus(QuestionStatus.NO_ANSWER).size();

        testResults = new TestResults(totalQuestions, correctAnswerQuestions, wrongAnswerQuestions, noAnswerQuestions);

        studentTestingPresentation.showTestResults(testResults);

        if(testResults.getPercentOfCorrectAnswers() >= appProps.getPercent_of_correct_answers_required()) {
            studentTestingPresentation.showMessage(AppMessageCodes.TEST_PASSED);
        } else {
            studentTestingPresentation.showMessage(AppMessageCodes.TEST_FAILED);
        }
    }

    private void tryPrepareQuestions() {
        try {
            questions = questionDao.readQuestions();
        } catch (Exception e) {
            studentTestingPresentation.showMessage(AppMessageCodes.TEST_EXCEPTION_LOAD_QUESTIONS);
        }
    }

    private void processQuestion(Question question) {
        studentTestingPresentation.displayQuestion(question);

        String answerString = studentTestingPresentation.readUserInput();
        boolean isCorrectAnswer = studentTestingPresentation.giveAnswer(question, answerString);

        if(isCorrectAnswer) {
            studentTestingPresentation.showMessage(AppMessageCodes.TEST_ANSWER_IS_CORRECT);
        } else {
            studentTestingPresentation.showMessage(AppMessageCodes.TEST_ANSWER_IS_WRONG);
            studentTestingPresentation.displayCorrectAnswer(question);
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
