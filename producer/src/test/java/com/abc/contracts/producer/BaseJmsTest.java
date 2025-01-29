package com.abc.contracts.producer;

import com.abc.contracts.producer.config.EmbeddedArtemisTestConfig;
import com.abc.contracts.producer.config.JmsTestConfig;
import com.abc.contracts.producer.domains.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {JmsTestConfig.class, EmbeddedArtemisTestConfig.class})
@AutoConfigureMessageVerifier
@Testcontainers
public abstract class BaseJmsTest {

    @Autowired
    protected JmsTemplate jmsTemplate;


    // This method will be called by the contract test
    protected void triggerPostMessage() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String jsonMessage = objectMapper.writeValueAsString(
                    new Post(1, "string", "string", 1,
                            LocalDateTime.parse("2025-01-28T21:58:21")));

            System.out.println("Queue name: post-queue");
            System.out.println("🔍 DEBUG: Sending message -> " + jsonMessage);

            MessageCreator messageCreator = session -> {
                TextMessage message = session.createTextMessage(jsonMessage);
                message.setStringProperty("_type", "com.abc.contracts.producer.domains.Post"); // Explicit type for SCC
                message.setJMSType("application/json"); // Ensures correct type
                return message;
            };

            jmsTemplate.send("post-queue", messageCreator);

            // ✅ Wait before SCC reads the message
            Thread.sleep(5000);

            // 🔥 Manually receive message before SCC runs
            Message receivedMessage = jmsTemplate.receive("post-queue");
            if (receivedMessage != null) {
                System.out.println("🔍 DEBUG: Message manually received before SCC -> " + receivedMessage);
            } else {
                System.out.println("❌ DEBUG: No message was found in queue before SCC.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to send message", e);
        }
    }
}
