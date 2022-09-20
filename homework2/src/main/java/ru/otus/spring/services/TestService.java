package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.dto.QuestionStatus;
import ru.otus.spring.domain.dto.TestResults;
import ru.otus.spring.domain.dto.User;
import ru.otus.spring.presentation.QuestionPresentation;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    private final QuestionPresentation questionPresentation;
    private final QuestionDao questionDao;
    private final double percentOfCorrectAnswersForCredit;
    private TestResults testResults;
    private List<Question> questions;
    private User user;

    @Autowired
    public TestService(QuestionPresentation questionPresentation, QuestionDao questionDao, @Value("${test.percent_of_correct_answers_for_credit}") int percentOfCorrectAnswersForCredit) {
        this.questionPresentation = questionPresentation;
        this.questionDao = questionDao;
        this.percentOfCorrectAnswersForCredit = percentOfCorrectAnswersForCredit;
    }

    public void runTest() {
        requestUserData();
        tryPrepareQuestions();
        questionPresentation.showMessage("The test has started!");

        for(Question question : questions) {
            processQuestion(question);
        }
        questionPresentation.showMessage(String.format("Thank you, %s,the test has finished!",user.getStringRepresentation()));
        reportTestResults();
    }

    private void requestUserData() {
        questionPresentation.showMessage("Enter your surname:");
        String surname = questionPresentation.readUserInput();

        questionPresentation.showMessage("Enter your name:");
        String name = questionPresentation.readUserInput();

        user = new User(name, surname);
    }

    private void reportTestResults() {
        int totalQuestions = questions.size();
        int correctAnswerQuestions = filterQuestionsByStatus(QuestionStatus.CORRECT_ANSWER).size();
        int wrongAnswerQuestions = filterQuestionsByStatus(QuestionStatus.WRONG_ANSWER).size();
        int noAnswerQuestions = filterQuestionsByStatus(QuestionStatus.NO_ANSWER).size();

        testResults = new TestResults(totalQuestions, correctAnswerQuestions, wrongAnswerQuestions, noAnswerQuestions);

        questionPresentation.showMessage(testResults.getStringRepresentation());

        if(testResults.getPercentOfCorrectAnswers() >= percentOfCorrectAnswersForCredit) {
            questionPresentation.showMessage("The test has been passed successfully!");
        } else {
            questionPresentation.showMessage("The test has been failed!");
        }
    }

    private void tryPrepareQuestions() {
        try {
            questions = questionDao.readQuestions();
        } catch (Exception e) {
            questionPresentation.showMessage("Failed to load questions.");
        }
    }

    private void processQuestion(Question question) {
        questionPresentation.displayQuestion(question);

        String answerString = questionPresentation.readUserInput();
        boolean isCorrectAnswer = questionPresentation.giveAnswer(question, answerString);

        if(isCorrectAnswer) {
            questionPresentation.showMessage("The answer is correct.");
        } else {
            questionPresentation.showMessage("The answer is wrong.");
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
