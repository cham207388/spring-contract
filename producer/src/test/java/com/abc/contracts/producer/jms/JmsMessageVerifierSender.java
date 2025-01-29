package com.abc.contracts.producer.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierSender;
import org.springframework.jms.core.JmsTemplate;

import java.util.Map;

public class JmsMessageVerifierSender implements MessageVerifierSender<Message> {

    private final JmsTemplate jmsTemplate;

    public JmsMessageVerifierSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void send(Message message, String destination, YamlContract contract) {
        System.out.println("Sending raw JMS message to destination " + destination);
        jmsTemplate.convertAndSend(destination, message);
    }

    /**
     * Send a generic payload to a destination with headers.
     */
    @Override
    public <T> void send(T payload, Map<String, Object> headers, String destination, YamlContract contract) {
        System.out.println("Sending message to '{}' with payload: {}"+ destination + " " + payload);

        jmsTemplate.send(destination, session -> {
            TextMessage textMessage = session.createTextMessage(payload.toString());
            headers.forEach((key, value) -> setHeaderSafely(textMessage, key, value));
            return textMessage;
        });
    }

    private void setHeaderSafely(Message message, String key, Object value) {
        try {
            if (value instanceof String stringValue) {
                message.setStringProperty(key, stringValue);
            } else if (value instanceof Number numberValue) {
                message.setObjectProperty(key, numberValue);
            } else {
                System.out.format("Unsupported header type for key %s %s", key, value);
            }
        } catch (JMSException e) {
            System.out.format("Failed to set header %s %s", key, e.getMessage());
        }
    }
}
