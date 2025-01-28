package com.abc.contracts.producer;

import com.abc.contracts.producer.config.MessagingTestConfig;
import com.abc.contracts.producer.domains.Post;
import com.abc.contracts.producer.services.PostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.RabbitMQContainer;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MessagingTestConfig.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@AutoConfigureMessageVerifier
public abstract class MessagingBase {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @MockitoBean
    protected PostService postService;

    private static RabbitMQContainer rabbitMQContainer;

    @BeforeEach
    void setup() {
        // Clear RabbitMQ queue before tests
        rabbitTemplate.receive("post-queue");
        Mockito.when(postService.save(Mockito.any(Post.class))).thenAnswer(invocation -> null);
    }

    @BeforeAll
    static void startRabbitMQ() {
        rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.11-management-alpine")
                .withExposedPorts(5672, 15672); // AMQP port and management UI
        rabbitMQContainer.start();

        System.setProperty("spring.rabbitmq.host", rabbitMQContainer.getHost());
        System.setProperty("spring.rabbitmq.port", rabbitMQContainer.getAmqpPort().toString());
    }

    public PostService getPostService() {
        return postService;
    }
}