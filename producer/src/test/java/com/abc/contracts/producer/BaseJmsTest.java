package com.abc.contracts.producer;

import com.abc.contracts.producer.config.EmbeddedArtemisTestConfig;
import com.abc.contracts.producer.config.JmsTestConfig;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {JmsTestConfig.class, EmbeddedArtemisTestConfig.class})
@AutoConfigureMessageVerifier
@Testcontainers
@ActiveProfiles("test") // Activate the "test" profile
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseJmsTest {

    @Autowired
    protected JmsTemplate jmsTemplate;

    @BeforeAll
    public static void startBroker() throws Exception {
        new EmbeddedArtemisTestConfig().embeddedActiveMQ(); // Start broker before tests
    }

    @AfterAll
    public static void stopBroker() {
        EmbeddedArtemisTestConfig.stopBroker(); // Stop broker after tests
    }

    protected void triggerPostMessage() {
        try {
            System.out.println("⏳ Waiting before sending message...");
            Thread.sleep(2000);  // ✅ Delay before sending to let the broker stabilize
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String payload = """
        {
            "id": 1,
            "title": "string",
            "content": "string",
            "userId": 1,
            "createdAt": "2025-01-28T21:58:21"
        }
    """;

        System.out.println("📤 Sending message to 'post-queue': " + payload);

        jmsTemplate.convertAndSend("post-queue", payload, message -> {
            message.setStringProperty("_type", "com.abc.contracts.producer.domains.Post");
            message.setStringProperty("JMSType", "application/json");
            message.setStringProperty("Content_Type", "application/json");
//            message.setStringProperty("Content-Type", "application/json");  // ✅ Ensure SCC recognizes it
            return message;
        });

        // Wait a bit after sending the message
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}