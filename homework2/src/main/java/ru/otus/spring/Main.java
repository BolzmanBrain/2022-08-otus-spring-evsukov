package ru.otus.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.services.TestService;

@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "ru.otus.spring")
public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TestService test = context.getBean(TestService.class);
        test.runTest();
    }
}
