package com.abc.contracts.producer.services;

import java.util.List;
import com.abc.contracts.producer.domains.Post;
import com.abc.contracts.producer.messaging.PostMessagePublisher;
import com.abc.contracts.producer.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMessagePublisher postMessagePublisher;

    public List<Post> getAllPosts() {
        log.info("Fetching all posts");

        return postRepository.findAll();
    }

    public List<Post> getPostsByUserid(int userId) {
        log.info("Fetching user: {} posts", userId);

        return postRepository.findAllByUserId(userId);
    }

    public Post save(Post post) {
        try {
            log.info("Publishing post message: {}", post);
            postMessagePublisher.publishPostMessage(post);
        } catch (Exception e) {
            log.error("Failed to publish post message", e);
        }
        Post response = postRepository.save(post);
        log.info("post response id: {}", response.getId());
        return response;
    }
    
    public List<Post> savePosts(List<Post> posts) {
        List<Post> response = postRepository.saveAll(posts);
        try {
            response.forEach(post -> {
                log.info("Publishing post: {}", post);
                postMessagePublisher.publishPostMessage(post);
            });
        } catch (Exception e) {
            log.error("Failed to publish post message", e);
        }
        return response;
    }

    public Post getPostByUserIdAndPostId(int id, int userId) {
        Post response = postRepository.findByIdAndUserId(id, userId);
        log.info("response id: {}", response.getId());
        return response;
    }
}
