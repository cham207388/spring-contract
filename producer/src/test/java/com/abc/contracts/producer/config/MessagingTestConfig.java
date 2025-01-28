package com.abc.contracts.producer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingTestConfig {

    @Bean("post-queue")
    public Queue postQueue() {
        return new Queue("post-queue");
    }
}
