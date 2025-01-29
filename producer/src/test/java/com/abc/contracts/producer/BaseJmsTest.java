package com.abc.contracts.producer;

import com.abc.contracts.producer.config.JmsTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = JmsTestConfig.class)
public abstract class BaseJmsTest {

    @Autowired
    protected JmsTemplate jmsTemplate;

    // This method will be called by the contract test
    protected void send() {
        jmsTemplate.convertAndSend("seat-reservation-queue",
                "{\"id\":1,\"title\":\"string\",\"content\":\"string\",\"userId\":1,\"createdAt\":\"2025-01-28T21:58:21\"}");
    }
}
