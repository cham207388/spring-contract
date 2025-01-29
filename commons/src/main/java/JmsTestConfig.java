//package com.abc.contracts.producer.config;
//
//import com.abc.contracts.producer.jms.JmsMessageVerifierReceiver;
//import com.abc.contracts.producer.jms.JmsMessageVerifierSender;
//import jakarta.jms.ConnectionFactory;
//import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
//import org.apache.activemq.junit.EmbeddedActiveMQBroker;
//import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
//import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.annotation.EnableJms;
//import org.springframework.jms.connection.CachingConnectionFactory;
//import org.springframework.jms.core.JmsTemplate;
//
//
//@EnableJms
//@Configuration
//public class JmsTestConfig {
//
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        ActiveMQConnectionFactory activeMQConnectionFactory =
//                new ActiveMQConnectionFactory("tcp://localhost:61616");
//        // Wrap ActiveMQConnectionFactory in a Spring-managed CachingConnectionFactory
//        return new CachingConnectionFactory(activeMQConnectionFactory);
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
//        return new JmsTemplate(connectionFactory);
//    }
//
//    @Bean
//    public JmsMessageVerifierSender jmsMessageVerifierSender(JmsTemplate jmsTemplate) {
//        return new JmsMessageVerifierSender(jmsTemplate);
//    }
//
//    @Bean
//    public JmsMessageVerifierReceiver jmsMessageVerifierReceiver(JmsTemplate jmsTemplate) {
//        return new JmsMessageVerifierReceiver(jmsTemplate);
//    }
//
//    @Bean
//    public ContractVerifierMessaging<?> contractVerifierMessaging(
//            JmsMessageVerifierSender sender,
//            JmsMessageVerifierReceiver receiver) {
//        return new ContractVerifierMessaging<>(sender, receiver);
//    }
//
//    @Bean
//    public ContractVerifierObjectMapper contractVerifierObjectMapper() {
//        return new ContractVerifierObjectMapper();
//    }
//
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public EmbeddedActiveMQBroker embeddedActiveMQBroker() {
//        return new EmbeddedActiveMQBroker();
//    }
//}
