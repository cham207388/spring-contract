package com.abc.contracts.producer.verifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierReceiver;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

@Service
public class StreamMessageVerifierReceiver implements MessageVerifierReceiver {

    private final QueueChannel inputChannel;
    private final ObjectMapper objectMapper;

    public StreamMessageVerifierReceiver(QueueChannel inputChannel, ObjectMapper objectMapper) {
        this.inputChannel = inputChannel;
        this.objectMapper = objectMapper;
    }

    @Override
    public Object receive(String destination, long timeout, TimeUnit timeUnit, @Nullable YamlContract contract) {
        return receive(destination, contract);
    }

    @Override
    public Object receive(String destination, YamlContract contract) {
        System.out.println("🔄 Waiting for message from " + destination);

        Message<?> receivedMessage = inputChannel.receive(5000); // 5 seconds timeout

        if (receivedMessage == null) {
            System.out.println("⚠️ No message received from " + destination);
            return null;
        }

        try {
            String jsonPayload = (String) receivedMessage.getPayload();
            System.out.println("✅ Received JSON: " + jsonPayload);
            return objectMapper.readTree(jsonPayload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse received JSON", e);
        }
    }
}
