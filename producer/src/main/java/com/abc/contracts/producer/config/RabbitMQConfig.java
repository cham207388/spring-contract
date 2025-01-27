package com.abc.contracts.producer.config;

import lombok.Getter;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.post.queue}")
    private String postQueue;

    @Bean
    public Queue postQueue() {
        return new Queue(postQueue, true);
    }
}