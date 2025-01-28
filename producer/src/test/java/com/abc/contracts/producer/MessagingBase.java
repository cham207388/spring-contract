package com.abc.contracts.producer;

import com.abc.contracts.producer.services.PostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.RabbitMQContainer;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMessageVerifier
public abstract class MessagingBase {

    private static RabbitMQContainer rabbitMQContainer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @BeforeAll
    static void startRabbitMQ() {
        // Start RabbitMQ container
        rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.11-management-alpine")
                .withExposedPorts(5672, 15672); // AMQP port and management UI
        rabbitMQContainer.start();

        // Set RabbitMQ properties for the Spring context
        System.setProperty("spring.rabbitmq.host", rabbitMQContainer.getHost());
        System.setProperty("spring.rabbitmq.port", String.valueOf(rabbitMQContainer.getAmqpPort()));
    }

    @BeforeEach
    void setup() {
        // Clear the post-queue before each test
        clearQueue("post-queue");
    }

    /**
     * Utility method to clear a specific RabbitMQ queue.
     *
     * @param queueName Name of the queue to clear.
     */
    public void clearQueue(String queueName) {
        while (rabbitTemplate.receive(queueName) != null) {
            // Consume and discard all messages in the queue
        }
    }

    /**
     * Utility method to receive a single message from a RabbitMQ queue.
     *
     * @param queueName Name of the queue to receive from.
     * @return The message body as a String, or null if no message is available.
     */
    public String receiveMessage(String queueName) {
        Message message = rabbitTemplate.receive(queueName);
        return message != null ? new String(message.getBody()) : null;
    }

    /**
     * Utility method to receive all messages from a RabbitMQ queue.
     *
     * @param queueName Name of the queue to receive from.
     * @return A list of message bodies as Strings.
     */
    public List<String> receiveAllMessages(String queueName) {
        List<String> messages = new ArrayList<>();
        Message message;
        while ((message = rabbitTemplate.receive(queueName)) != null) {
            messages.add(new String(message.getBody()));
        }
        return messages;
    }

    /**
     * Utility method to send a message to a RabbitMQ queue.
     *
     * @param exchange  Name of the exchange.
     * @param routingKey Routing key to use.
     * @param message    The message body as a String.
     */
    public void sendMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    /**
     * Mock the behavior of the PostService.
     * This is useful for contract tests that rely on triggering actions from the service.
     */
    public void mockPostServiceBehavior() {
        PostService postService = Mockito.mock(PostService.class);
        Mockito.when(postService.save(Mockito.any())).thenAnswer(invocation -> {
            // Add any mocked behavior here, if necessary
            return null;
        });
    }
}