package com.abc.contracts.producer.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierReceiver;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

public class JmsMessageVerifierReceiver implements MessageVerifierReceiver<Message> {

    private final JmsTemplate jmsTemplate;

    public JmsMessageVerifierReceiver(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Receive a message from the queue with a timeout.
     */
    @Override
    public Message receive(String destination, long timeout, TimeUnit timeUnit, @Nullable YamlContract contract) {
        System.out.format("Receiving message from %s with timeout %s %s%n", destination, timeout, timeUnit);
        jmsTemplate.setReceiveTimeout(timeUnit.toMillis(timeout));
        Message message = jmsTemplate.receive(destination);
        if (message != null) {
            System.out.println("Received message payload: " + extractPayload(message));
        } else {
            System.out.println("No message received from destination: " + destination);
        }
        return message;
    }

    /**
     * Receive a message from the queue (no timeout).
     */
    @Override
    public Message receive(String destination) {
        System.out.format("Receiving message from %s%n", destination);
        Message message = jmsTemplate.receive(destination);
        if (message != null) {
            System.out.println("Received message payload: " + extractPayload(message));
        } else {
            System.out.println("No message received from destination: " + destination);
        }
        return message;
    }

    @Override
    public Message receive(String destination, YamlContract contract) {
        return receive(destination);
    }

    /**
     * Extract the payload from the JMS message.
     */
    public String extractPayload(Message message) {
        try {
            if (message instanceof TextMessage textMessage) {
                return textMessage.getText();
            }
        } catch (JMSException e) {
            System.out.format("Failed to extract payload from JMS message %s", e);
        }
        return null;
    }
}