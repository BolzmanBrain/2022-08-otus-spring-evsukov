package ru.otus.spring.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.dto.QuestionTypes;
import ru.otus.spring.exceptions.UnknownQuestionTypeIsProvided;

@Service
public class AnswerInstructionProviderServiceConsole implements AnswerInstructionProviderService {
    @Override
    public String getAnswerInstruction(String questionTypeId) {
        switch (questionTypeId) {
            case QuestionTypes.TEXT_QUESTION:
                return QUESTION_TEXT_QUESTION_ANSWER_INSTRUCTION;

            case QuestionTypes.SINGLE_SELECT_QUESTION:
                return QUESTION_SINGLE_SELECT_QUESTION_ANSWER_INSTRUCTION;

            case QuestionTypes.MULTI_SELECT_QUESTION:
                return QUESTION_MULTI_SELECT_QUESTION_ANSWER_INSTRUCTION;

            default:
                throw new UnknownQuestionTypeIsProvided("Unknown question type", null);

        }
    }

    private static final String QUESTION_TEXT_QUESTION_ANSWER_INSTRUCTION = "question.text_question_answer_instruction";
    private static final String QUESTION_SINGLE_SELECT_QUESTION_ANSWER_INSTRUCTION = "question.single_select_question_answer_instruction";
    private static final String QUESTION_MULTI_SELECT_QUESTION_ANSWER_INSTRUCTION = "question.multi_select_question_answer_instruction";
}
