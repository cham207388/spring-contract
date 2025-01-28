package com.abc.contracts.producer;

import com.abc.contracts.producer.config.MessagingConfig;
import com.abc.contracts.producer.controllers.PostController;
import com.abc.contracts.producer.repository.PostRepository;
import com.abc.contracts.producer.services.PostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.RabbitMQContainer;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMessageVerifier
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class, // Exclude database auto-configuration
        HibernateJpaAutoConfiguration.class // Exclude Hibernate auto-configuration
})
@ContextConfiguration(classes = MessagingConfig.class)
public abstract class MessagingBase {

    private static RabbitMQContainer rabbitMQContainer;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private PostController postController;

    @MockitoBean
    private PostRepository postRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setup() {
        // Clear messages in the post-queue
        while (rabbitTemplate.receive("post-queue") != null) {
            // Discard messages
        }
    }

    @BeforeAll
    static void startRabbitMQ() {
        // Start RabbitMQ TestContainer
        rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.11-management-alpine")
                .withExposedPorts(5672, 15672); // AMQP port and management UI port
        rabbitMQContainer.start();

        // Set RabbitMQ connection properties
        System.setProperty("spring.rabbitmq.host", rabbitMQContainer.getHost());
        System.setProperty("spring.rabbitmq.port", rabbitMQContainer.getAmqpPort().toString());
    }

    public PostService getPostService() {
        return postService;
    }
}