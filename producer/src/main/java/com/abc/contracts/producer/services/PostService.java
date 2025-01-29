package com.abc.contracts.producer.services;

import java.util.List;
import com.abc.contracts.producer.domains.Post;
import com.abc.contracts.producer.message.PostMessagePublisher;
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
        Post response = postRepository.save(post);
        log.info("post response id: {}", response.getId());
        postMessagePublisher.publishPost(response);
        return response;
    }
    
    public List<Post> savePosts(List<Post> posts) {
        List<Post> savePosts =  postRepository.saveAll(posts);
        log.info("Saved {} posts", savePosts.size());
        savePosts.forEach(postMessagePublisher::publishPost);
        return savePosts;
    }

    public Post getPostByUserIdAndPostId(int id, int userId) {
        Post response = postRepository.findByIdAndUserId(id, userId);
        log.info("response id: {}", response.getId());
        return response;
    }
}
