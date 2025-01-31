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
        EmbeddedActiveMQ embeddedActiveMQ = new EmbeddedArtemisTestConfig().embeddedActiveMQ();
        System.out.println("Broker started successfully!");
        embeddedActiveMQ.start();
    }

    protected void triggerPostMessage() {
        String payload = "{\"id\":1,\"title\":\"string\",\"content\":\"string\",\"userId\":1,\"createdAt\":\"2025-01-28T21:58:21\"}";
        jmsTemplate.convertAndSend("post-queue", payload, message -> {
            // Set the headers expected by the contract
            message.setStringProperty("_type", "com.abc.contracts.producer.domains.Post");
            message.setStringProperty("JMSType", "application/json");
            message.setStringProperty("Content_Type", "application/json");
            return message;
        });
    }
}