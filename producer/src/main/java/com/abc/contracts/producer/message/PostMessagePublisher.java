package com.abc.contracts.producer.message;


import com.abc.contracts.producer.domains.Post;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class PostMessagePublisher {

    private final JmsTemplate jmsTemplate;

    public PostMessagePublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void publishPostMessage(Post post) {
        jmsTemplate.convertAndSend("post-queue", post);
    }
}
