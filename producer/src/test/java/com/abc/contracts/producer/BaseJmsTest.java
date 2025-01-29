package com.abc.contracts.producer;

import com.abc.contracts.producer.config.EmbeddedArtemisTestConfig;
import com.abc.contracts.producer.config.JmsTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {JmsTestConfig.class, EmbeddedArtemisTestConfig.class})
@AutoConfigureMessageVerifier
public abstract class BaseJmsTest {

    @Autowired
    protected JmsTemplate jmsTemplate;

    // This method will be called by the contract test
    protected void triggerPostMessage() {
        System.out.println("Queue name: " + "post-queue");
        jmsTemplate.convertAndSend("post-queue",
                "{\"id\":1,\"title\":\"string\",\"content\":\"string\",\"userId\":1,\"createdAt\":\"2025-01-28T21:58:21\"}");
    }
}
