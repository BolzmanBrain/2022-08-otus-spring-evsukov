package ru.otus.spring.services;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.QuestionStatus;
import ru.otus.spring.presentation.QuestionPresentation;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private final QuestionPresentation questionPresentation;
    private final QuestionDao questionDao;

    private List<Question> questions;

    public Test(QuestionPresentation questionPresentation, QuestionDao questionDao) {
        this.questionPresentation = questionPresentation;
        this.questionDao = questionDao;
    }

    public void runTest() {
        tryPrepareQuestions();
        questionPresentation.showMessage("The test has started!");

        for(Question question : questions) {
            processQuestion(question);
        }
        questionPresentation.showMessage("The test has finished!");
        reportTestResults();
    }

    public void reportTestResults() {
        int totalQuestions = questions.size();
        int correctAnswerQuestions = filterQuestionsByStatus(QuestionStatus.CORRECT_ANSWER).size();
        int wrongAnswerQuestions = filterQuestionsByStatus(QuestionStatus.WRONG_ANSWER).size();
        int noAnswerQuestions = filterQuestionsByStatus(QuestionStatus.NO_ANSWER).size();

        questionPresentation.showMessage(String.format("\nTotal questions: %d\n" +
                "Questions with a correct answer: %d\n" +
                "Questions with a wrong answer: %d\n" +
                "Questions with no answer: %d",
                totalQuestions, correctAnswerQuestions, wrongAnswerQuestions, noAnswerQuestions));
    }

    private void tryPrepareQuestions() {
        try {
            questions = questionDao.readQuestions();
        } catch (Exception e) {
            questionPresentation.showMessage("Failed to load questions.");
            return;
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
