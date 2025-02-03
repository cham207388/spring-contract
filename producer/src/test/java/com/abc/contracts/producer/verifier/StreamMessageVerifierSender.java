package com.abc.contracts.producer.verifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierSender;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Map;

@Service
public class StreamMessageVerifierSender implements MessageVerifierSender {

    private final MessageChannel outputChannel;
    private final ObjectMapper objectMapper;

    public StreamMessageVerifierSender(MessageChannel outputChannel, ObjectMapper objectMapper) {
        this.outputChannel = outputChannel;
        this.objectMapper = objectMapper;
    }

    @Override
    public void send(Object payload, String destination) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            System.out.println("📤 Sending JSON message: " + jsonPayload);

            Message<String> message = MessageBuilder.withPayload(jsonPayload)
                    .setHeader("Content-Type", "application/json")
                    .build();

            outputChannel.send(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload", e);
        }
    }

    @Override
    public void send(Object payload, Map headers, String destination, @Nullable YamlContract contract) {
        send(payload, destination);
    }

    @Override
    public void send(Object message, String destination, @Nullable YamlContract contract) {
        send(message, destination);
    }
}

