package ru.otus.spring;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.spring.config.AppProps;

@SpringBootApplication
@EnableMongock
@EnableMongoRepositories
@EnableConfigurationProperties(AppProps.class)
public class LibraryApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(LibraryApplication.class, args);
		Console.main(args);
	}
}
