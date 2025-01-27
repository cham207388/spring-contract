package com.abc.contracts.consumer.messaging;

import com.abc.contracts.consumer.domains.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PostMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public PostMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishPostMessage(Post post) {
        log.info("Publishing {}", post);
        rabbitTemplate.convertAndSend("post-queue", post);
        log.info("Post {} published to queue", post);
    }
}