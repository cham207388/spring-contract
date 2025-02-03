package com.abc.contracts.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@Configuration
public class StreamConfig {

    @Bean
    public MessageChannel outputChannel() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public QueueChannel inputChannel() {
        return MessageChannels.queue().getObject();
    }

    @Bean
    public IntegrationFlow processOrderFlow() {
        return IntegrationFlow.from(outputChannel())
                .channel(inputChannel()) // Send messages from outputChannel to inputChannel
                .get();
    }


    @ServiceActivator(inputChannel = "outputChannel")
    public void logMessages(Message<?> message) {
        System.out.println("📥 Message received on outputChannel: " + message.getPayload());
    }
}
