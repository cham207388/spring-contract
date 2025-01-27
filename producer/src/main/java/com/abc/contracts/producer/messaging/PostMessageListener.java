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
        log.info("Received rabbitListener: {}", post.getTitle());
        log.info("Received rabbitListener: {}", post.getContent());
        log.info("Received rabbitListener: {}", post.getUserId());
        Post rabbitListener = new Post(post.getTitle(), post.getContent(), post.getUserId());
        log.info("Received rabbitListener: {}", rabbitListener);
//        postService.saveRabbit(post);
        log.info("Saved post: {}", post);
    }
}
