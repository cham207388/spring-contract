package com.abc.contracts.producer.messaging;

import com.abc.contracts.producer.domains.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostMessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void publishPostMessage(Post post) {
        try {
            // Convert the Post object to a JSON string
            String message = objectMapper.writeValueAsString(post);

            // Publish the message to the queue
            rabbitTemplate.convertAndSend("post-exchange", "post-key", message);

            log.info("Published message: {}", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize message", e);
        }
    }
}