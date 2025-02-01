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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {JmsTestConfig.class, EmbeddedArtemisTestConfig.class})
@AutoConfigureMessageVerifier
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseJmsTest {

    @Autowired
    protected JmsTemplate jmsTemplate;

    private final CountDownLatch messageLatch = new CountDownLatch(1);

    @BeforeAll
    public static void startBroker() throws Exception {
        new EmbeddedArtemisTestConfig().embeddedActiveMQ(); // Start broker before tests
    }

    @AfterAll
    public static void stopBroker() {
        EmbeddedArtemisTestConfig.stopBroker(); // Stop broker after tests
    }

    protected void triggerPostMessage() throws InterruptedException {
        String payload = "{\"id\":1,\"title\":\"string\",\"content\":\"string\",\"userId\":1,\"createdAt\":\"2025-01-28T21:58:21\"}";
        jmsTemplate.convertAndSend("post-queue", payload, message -> {
            message.setStringProperty("_type", "com.abc.contracts.producer.domains.Post");
            message.setStringProperty("JMSType", "application/json");
            message.setStringProperty("Content_Type", "application/json");
            return message;
        });
        System.out.println("Message sent to post-queue");

        // Signal that the message has been sent
        messageLatch.countDown();
    }

    protected void waitForMessage() throws InterruptedException {
        // Wait for the message to be sent (with a timeout to avoid hanging)
        boolean messageSent = messageLatch.await(10, TimeUnit.SECONDS);
        if (!messageSent) {
            throw new RuntimeException("Timeout waiting for message to be sent");
        }
    }
}