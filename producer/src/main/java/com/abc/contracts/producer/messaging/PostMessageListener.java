package com.abc.contracts.producer.messaging;

import com.abc.contracts.producer.domains.Post;
import com.abc.contracts.producer.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostMessageListener {

    private final PostService postService;

    @RabbitListener(queues = "post-queue")
    public void handlePostMessage(Post post) {
        log.info("Received post: {}", post);
        postService.saveRabbit(post);
        log.info("Saved post: {}", post);
    }
}
