package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Professional;
import ru.otus.spring.domain.Student;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentProviderServiceImpl implements StudentProviderService {
    private final static String[] NAMES = {"Mike","Jane","Alexander","Regina","Petya","Kate"};
    private final static String[] SURNAMES = {"Jonhnson","Ivanenko","Shevshuk","Krasovich","Smith","Text"};
    private final static int NUMBER_OF_STUDENTS_TO_GENERATE = 10;
    private final OnlineSchoolGateway onlineSchoolGateway;

    @Override
    public void generateStudentsAndPassThemToChannel() {
        for(int i = 0; i < NUMBER_OF_STUDENTS_TO_GENERATE; i++) {
            Student student = generateStudent();
            System.out.println("");
            log.info("Before: {}, salary: {}", student.getFullName(), student.getSalaryInDollars());
            Professional professional = onlineSchoolGateway.process(student);
            log.info("After: {}, salary: {}", professional.getFullName(), professional.getSalaryInDollars());

            delay();
        }
    }

    private static Student generateStudent() {
        String name = NAMES[RandomUtils.nextInt(0, NAMES.length)];
        String surname = SURNAMES[RandomUtils.nextInt(0, SURNAMES.length)];
        int salary = RandomUtils.nextInt(50, 1000);
        return new Student(name+" "+surname, salary);
    }

    private void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
