package com.abc.contracts.producer;

import com.abc.contracts.producer.config.EmbeddedArtemisTestConfig;
import com.abc.contracts.producer.config.JmsTestConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {JmsTestConfig.class, EmbeddedArtemisTestConfig.class})
@AutoConfigureMessageVerifier
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseJmsTest {

    @Autowired
    protected JmsTemplate jmsTemplate;

    private static final AtomicBoolean isMessageSent = new AtomicBoolean(false);
    private final CountDownLatch messageLatch = new CountDownLatch(1);

    @BeforeAll
    public static void startBroker() throws Exception {
        System.out.println("🚀 Starting embedded ActiveMQ broker...");
        new EmbeddedArtemisTestConfig().embeddedActiveMQ();
        Thread.sleep(3000); // Allow broker startup delay
    }

    @AfterAll
    public static void stopBroker() {
        System.out.println("🛑 Stopping embedded ActiveMQ broker...");
        EmbeddedArtemisTestConfig.stopBroker();
    }

    @BeforeEach
    public void setup() throws InterruptedException {
        if (isMessageSent.compareAndSet(false, true)) {  // Ensures this block runs only once
            System.out.println("📤 Sending test message before SCC runs verification");
            waitForMessage();
            System.out.println("✅ Test message sent successfully!");
        } else {
            System.out.println("🛑 Message already sent, skipping...");
        }
    }

    protected void triggerPostMessage()  {
        String payload = "{\"id\":1,\"title\":\"string\",\"content\":\"string\",\"userId\":1,\"createdAt\":\"2025-01-28T21:58:21\"}";
        jmsTemplate.convertAndSend("post-queue", payload, message -> {
            message.setStringProperty("_type", "com.abc.contracts.producer.domains.Post");
            message.setStringProperty("JMSType", "application/json");
            message.setStringProperty("Content_Type", "application/json");
            return message;
        });
        System.out.println("✅ Message sent to 'post-queue'");

        // Signal that the message has been sent
        messageLatch.countDown();
    }

    protected void waitForMessage() throws InterruptedException {
        boolean messageSent = messageLatch.await(10, TimeUnit.SECONDS);
        if (!messageSent) {
            throw new RuntimeException("❌ Timeout waiting for message to be sent");
        }
    }
}