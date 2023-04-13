package ru.otus.spring.config;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dtos.BookDto;
import ru.otus.spring.service.DatastoreLifecycleService;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 1;
    private final Logger logger = LoggerFactory.getLogger("Batch");
    public static final String TRANSFER_BOOKS_JOB_NAME = "transferBooksJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DatastoreLifecycleService datastoreLifecycleService;
    @Autowired
    private SessionFactory sessionFactory;

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

    @Bean
    public MethodInvokingTaskletAdapter beforeAllTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(datastoreLifecycleService);
        adapter.setTargetMethod("doBeforeAll");

        return adapter;
    }

    @Bean
    public MethodInvokingTaskletAdapter afterAllTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(datastoreLifecycleService);
        adapter.setTargetMethod("doAfterAll");

        return adapter;
    }

    @Bean
    public Step beforeAllStep() {
        return this.stepBuilderFactory.get("beforeAllStep")
                .tasklet(beforeAllTasklet())
                .build();
    }

    @Bean
    public Step afterAllStep() {
        return this.stepBuilderFactory.get("afterAllStep")
                .tasklet(afterAllTasklet())
                .build();
    }

    @Bean
    public MongoItemReader<Author> authorReader(MongoTemplate mongoTemplate) {
        MongoItemReader<Author> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setTargetType(Author.class);
        reader.setQuery("{ }");
        reader.setCollection("authors");
        reader.setName("authorReader");
        reader.setSort(new HashMap<String, Sort.Direction>());
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<Author> authorWriter(final DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Author>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO stg_authors_tbl(uuid, name) VALUES (:id, :name)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Step transformAuthorsStep(MongoItemReader<Author> reader, JdbcBatchItemWriter<Author> writer) {
        return stepBuilderFactory.get("transformAuthorsStep")
                .<Author, Author>chunk(CHUNK_SIZE)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public MongoItemReader<Book> bookReader(MongoTemplate mongoTemplate) {
        MongoItemReader<Book> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setTargetType(Book.class);
        reader.setQuery("{ }");
        reader.setCollection("books");
        reader.setName("bookReader");
        reader.setSort(new HashMap<String, Sort.Direction>());
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<BookDto> bookWriter(final DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<BookDto>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO stg_books_tbl(uuid, name, uuid_author) VALUES (:id, :name, :idAuthor)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public ItemProcessor<Book, BookDto> bookProcessor() {
        return BookDto::createFromDomainObject;
    }

    @Bean
    public Step transformBooksStep(MongoItemReader<Book> reader, JdbcBatchItemWriter<BookDto> writer,
                                   ItemProcessor<Book, BookDto> processor) {
        return stepBuilderFactory.get("transformBooksStep")
                .<Book, BookDto>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job transferBooksJob(Step beforeAllStep, Step transformAuthorsStep, Step transformBooksStep, Step afterAllStep) {
        return jobBuilderFactory.get(TRANSFER_BOOKS_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(beforeAllStep)
                .next(transformAuthorsStep)
                .next(transformBooksStep)
                .next(afterAllStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }
}
