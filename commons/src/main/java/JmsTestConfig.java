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

//import com.abc.contracts.producer.config.EmbeddedArtemisTestConfig;
//import com.abc.contracts.producer.config.JmsTestConfig;
//import com.abc.contracts.producer.domains.Post;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import jakarta.jms.Message;
//import jakarta.jms.TextMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.time.LocalDateTime;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@ContextConfiguration(classes = {JmsTestConfig.class, EmbeddedArtemisTestConfig.class})
//@AutoConfigureMessageVerifier
//@Testcontainers
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public abstract class BaseJmsTest {
//
//    @Autowired
//    protected JmsTemplate jmsTemplate;


    // This method will be called by the contract test
//    protected void triggerPostMessage() {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.registerModule(new JavaTimeModule());
//            String jsonMessage = objectMapper.writeValueAsString(
//                    new Post(1, "string", "string", 1,
//                            LocalDateTime.parse("2025-01-28T21:58:21")));
//
//            System.out.println("Queue name: post-queue");
//            System.out.println("🔍 DEBUG: Sending message -> " + jsonMessage);
//
//            MessageCreator messageCreator = session -> {
//                TextMessage message = session.createTextMessage(jsonMessage);
//                message.setStringProperty("_type", "com.abc.contracts.producer.domains.Post"); // Explicit type for SCC
//                message.setJMSType("application/json"); // Ensures correct type
//                return message;
//            };
//
//            jmsTemplate.send("post-queue", messageCreator);
//
//            // ✅ Wait before SCC reads the message
//            Thread.sleep(5000);
//
//            // 🔥 Manually receive message before SCC runs
//            Message receivedMessage = jmsTemplate.receive("post-queue");
//            if (receivedMessage != null) {
//                System.out.println("🔍 DEBUG: Message manually received before SCC -> " + receivedMessage);
//            } else {
//                System.out.println("❌ DEBUG: No message was found in queue before SCC.");
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to send message", e);
//        }
}
