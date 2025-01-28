package com.abc.contracts.producer;

import com.abc.contracts.producer.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMessageVerifier
public abstract class MessagingBase {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    protected PostService postService;

    @BeforeEach
    void setup() {
        // Clear RabbitMQ queue before tests
        rabbitTemplate.receive("post-queue");
    }

    public PostService getPostService() {
        return postService;
    }
}