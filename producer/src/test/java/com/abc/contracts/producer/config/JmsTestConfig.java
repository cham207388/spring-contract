package com.abc.contracts.producer.config;

import com.abc.contracts.producer.jms.JmsMessageVerifierReceiver;
import com.abc.contracts.producer.jms.JmsMessageVerifierSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@Configuration
public class JmsTestConfig {

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("vm://0");
    }

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setReceiveTimeout(5000);
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
    public ContractVerifierMessaging<?> contractVerifierMessaging(
            JmsMessageVerifierSender sender,
            JmsMessageVerifierReceiver receiver) {
        return new ContractVerifierMessaging<>(sender, receiver);
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