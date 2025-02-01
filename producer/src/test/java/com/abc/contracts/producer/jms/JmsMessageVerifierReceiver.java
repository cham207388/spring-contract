package com.abc.contracts.producer.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierReceiver;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Nullable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JmsMessageVerifierReceiver implements MessageVerifierReceiver<Message> {

    private final JmsTemplate jmsTemplate;

    public JmsMessageVerifierReceiver(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Message receive(String destination, long timeout, TimeUnit timeUnit, @Nullable YamlContract contract) {
        System.out.format("Receiving message from %s with timeout %s %s%n", destination, timeout, timeUnit);
        jmsTemplate.setReceiveTimeout(timeUnit.toMillis(timeout));

        Message message = null;
        int maxRetries = 5;  // ✅ Increase this if necessary
        int retryDelay = 1000; // 1 second between retries

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            message = jmsTemplate.receive(destination);
            if (message != null) {
                System.out.printf("✅ [Attempt %d/%d] Received message from '%s'%n", attempt, maxRetries, destination);
                System.out.println("Received message payload: " + extractPayload(message));
                System.out.println("Received message headers: " + extractHeaders(message));
                return message;
            } else {
                System.out.printf("⏳ [Attempt %d/%d] No message yet. Retrying in %dms...%n", attempt, maxRetries, retryDelay);
                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        System.out.println("❌ No message received from destination: " + destination + " after " + maxRetries + " attempts.");
        return null;
    }

    @Override
    public Message receive(String destination) {
        return receive(destination, 5, TimeUnit.SECONDS, null);
    }

    @Override
    public Message receive(String destination, YamlContract contract) {
        return receive(destination);
    }

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

    public Map<String, Object> extractHeaders(Message message) {
        Map<String, Object> headers = new HashMap<>();
        try {
            Enumeration<?> propertyNames = message.getPropertyNames();
            while (propertyNames.hasMoreElements()) {
                String key = (String) propertyNames.nextElement();
                headers.put(key, message.getObjectProperty(key));
            }
            System.out.println("🔹 Extracted headers: " + headers);
        } catch (JMSException e) {
            System.out.println("Failed to extract headers: " + e.getMessage());
        }
        return headers;
    }
}