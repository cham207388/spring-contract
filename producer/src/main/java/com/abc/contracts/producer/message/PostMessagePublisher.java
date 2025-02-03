package com.abc.contracts.producer.message;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.abc.contracts.producer.domains.Post;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostMessagePublisher {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public void publishPostString(Post post) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(post);
            log.info("Published post: {}", jsonMessage);
            jmsTemplate.convertAndSend("post.queue", jsonMessage);
        } catch (Exception e) {
            log.error("Error publishing post: {}", post);
        }
    }

    public void publishPost(Post post) {
        log.info("Published post object: {}", post);
        jmsTemplate.convertAndSend("post.queue", post);
    }
}
