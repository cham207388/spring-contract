package com.abc.contracts.producer.config;

import com.abc.contracts.producer.jms.JmsMessageVerifierReceiver;
import com.abc.contracts.producer.jms.JmsMessageVerifierSender;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class JmsTestConfig {

    // Use in-memory Artemis broker instead of ActiveMQ Classic
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(new ActiveMQConnectionFactory("vm://0"));
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
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
    public ContractVerifierMessaging<?> contractVerifierMessaging(
            JmsMessageVerifierSender sender,
            JmsMessageVerifierReceiver receiver) {
        return new ContractVerifierMessaging<>(sender, receiver);
    }

    @Bean
    public ContractVerifierObjectMapper contractVerifierObjectMapper() {
        return new ContractVerifierObjectMapper();
    }
}