package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.service.StudentProviderService;

@SpringBootApplication
public class LibraryAppApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(LibraryAppApplication.class, args);
		StudentProviderService studentProviderService = ctx.getBean(StudentProviderService.class);
		studentProviderService.generateStudentsAndPassThemToChannel();
	}
}
