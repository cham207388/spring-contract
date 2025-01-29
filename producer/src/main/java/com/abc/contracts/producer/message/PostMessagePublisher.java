package com.abc.contracts.producer.message;

import com.abc.contracts.producer.domains.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostMessagePublisher {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public void publishPostMessage(Post post) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(post);
            log.info("Published post: {}", jsonMessage);
            jmsTemplate.convertAndSend("post-queue", jsonMessage);
        } catch (Exception e) {
            log.error("Error publishing post: {}", post);
        }
    }
}
