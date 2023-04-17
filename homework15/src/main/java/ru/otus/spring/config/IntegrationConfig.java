package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.service.OnlineSchoolService;

@Configuration
public class IntegrationConfig {
    @Bean
    public QueueChannel studentsChannel() {
        return MessageChannels.queue(10).get();
    }

    /*This is the reply channel for the MessageGateway.
    * We can't receive replies from the same channel without creating the second MessageGateway.
    * Moreover, if we don't receive the replies, then the main channel is blocked because it's not read.
    * In fact, we can only receive messages from the same channel if there's no delay in the thread. */
    @Bean
    public PublishSubscribeChannel professionalsChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(1000).maxMessagesPerPoll(1).get();
    }

    @Bean
    public IntegrationFlow onlineSchoolFlow(OnlineSchoolService onlineSchoolService) {
        return IntegrationFlows.from(studentsChannel())
                .handle(onlineSchoolService, "educate") // Service activator
                .channel(professionalsChannel())
                .get();
    }
}
