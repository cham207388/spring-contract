package com.abc.contracts.producer;

import com.abc.contracts.producer.config.EmbeddedArtemisTestConfig;
import com.abc.contracts.producer.config.JmsTestConfig;
import com.abc.contracts.producer.jms.JmsMessageVerifierReceiver;
import com.abc.contracts.producer.jms.JmsMessageVerifierSender;
import jakarta.jms.Message;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = {JmsTestConfig.class, EmbeddedArtemisTestConfig.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
class JmsVerifierTest {

    @Autowired
    private JmsMessageVerifierSender sender;

    @Autowired
    private JmsMessageVerifierReceiver receiver;

    @BeforeAll
    public static void startBroker() throws Exception {
        EmbeddedActiveMQ embeddedActiveMQ = new EmbeddedArtemisTestConfig().embeddedActiveMQ();
        System.out.println("Broker started successfully!");
        embeddedActiveMQ.start();
    }

    @Test
    void testSendAndReceive() {
        String destination = "post-queue";
        String payload = "{\"id\":1,\"title\":\"string\",\"content\":\"string\",\"userId\":1,\"createdAt\":\"2025-01-28T21:58:21\"}";
        Map<String, Object> headers = Map.of(
                "_type", "com.abc.contracts.producer.domains.Post",
                "JMSType", "application/json",
                "Content-Type", "application/json"
        );

        System.out.println("Sending message to destination: " + destination);
        sender.send(payload, headers, destination, null);

        System.out.println("Receiving message from destination: " + destination);
        Message receivedMessage = receiver.receive(destination, 5, TimeUnit.SECONDS, null);

        assertNotNull(receivedMessage);
        assertEquals(payload, receiver.extractPayload(receivedMessage));
    }
}