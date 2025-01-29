package com.abc.contracts.producer.config;

import com.abc.contracts.producer.jms.JmsMessageVerifierReceiver;
import com.abc.contracts.producer.jms.JmsMessageVerifierSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@Configuration
public class JmsTestConfig {

    // Use in-memory Artemis broker instead of ActiveMQ Classic
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(new ActiveMQConnectionFactory("vm://0"));
    }

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        // 🔥 FIX: Register JavaTimeModule so Jackson understands LocalDateTime
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        converter.setObjectMapper(objectMapper); // <-- Important!
        return converter;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter()); // 🚀 Apply JSON converter
        return jmsTemplate;
    }

    @Bean
    public JmsMessageVerifierSender jmsMessageVerifierSender(JmsTemplate jmsTemplate) {
        return new JmsMessageVerifierSender(jmsTemplate);
    }

    @Bean
    public JmsMessageVerifierReceiver jmsMessageVerifierReceiver(JmsTemplate jmsTemplate) {
        return new JmsMessageVerifierReceiver(jmsTemplate);
    }

    @Bean
    public ContractVerifierObjectMapper contractVerifierObjectMapper() {
        return new ContractVerifierObjectMapper();
    }

    @Bean
    public ContractVerifierMessaging<?> contractVerifierMessaging(JmsTemplate jmsTemplate) {
        ContractVerifierMessaging<?> messaging = new ContractVerifierMessaging<>(
                new JmsMessageVerifierSender(jmsTemplate),
                new JmsMessageVerifierReceiver(jmsTemplate)
        );

        messaging.receive("post-queue"); // 🔥 Force SCC to listen to the queue
        return messaging;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("1-1");
        factory.setMessageConverter(jacksonJmsMessageConverter());
        return factory;
    }
}